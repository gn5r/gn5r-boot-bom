package com.github.gn5r.boot.mail.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.github.gn5r.boot.mail.model.MailModel;

import ch.astorm.jotlmsg.OutlookMessage;
import ch.astorm.jotlmsg.OutlookMessageRecipient.Type;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailService {

  @Autowired
  private MailProperties properties;

  /**
   * emlファイル形式のデータを作成しOutputStreamに書き込む
   * 
   * @param mailModel メールモデル
   * @param os        OutputStream
   * @throws MessagingException
   * @throws IOException
   */
  public void createEmlFile(MailModel mailModel, OutputStream os) throws MessagingException, IOException {
    MimeMessage mimeMessage = this.createMimeMessage(mailModel);
    mimeMessage.writeTo(os);
  }

  private Properties getMailProperties(MailModel mailModel) {
    Properties props = System.getProperties();
    props.setProperty("mail.mine.encodefilename", "true");
    props.setProperty("mail.mine.charset",
        StringUtils.defaultString(mailModel.getCharset(), StandardCharsets.UTF_8.name()));
    if (StringUtils.isNotEmpty(properties.getHost())) {
      props.setProperty("mail.smtp.host", properties.getHost());
    }
    if (properties.getPort() != null) {
      props.setProperty("mail.smtp.port", String.valueOf(properties.getPort()));
    }
    if (StringUtils.isNotEmpty(properties.getUsername())) {
      props.setProperty("mail.smtp.user", properties.getUsername());
    }
    if (StringUtils.isNotEmpty(properties.getPassword())) {
      props.setProperty("mail.smtp.password", properties.getPassword());
    }
    properties.getProperties().forEach((key, value) -> {
      log.debug("mail properties: " + key + "=" + value);
      if (StringUtils.isNotEmpty(value)) {
        props.setProperty(key, value);
      }
    });
    List<String> mailProps = props.keySet().stream().filter(key -> key.toString().startsWith("mail")).map(key -> {
      return key.toString() + "=" + props.get(key);
    }).collect(Collectors.toList());
    log.debug("Config mail properties:" + String.join(", ", mailProps));
    return props;
  }

  /**
   * {@linkplain MimeMessage}を作成する
   * 
   * @param mailModel メールモデル
   * @return MimeMessage
   * @throws MessagingException
   */
  private MimeMessage createMimeMessage(MailModel mailModel) throws MessagingException {
    Properties props = this.getMailProperties(mailModel);
    Session session = Session.getInstance(props);
    boolean isAuth = "true".equals(props.getProperty("mail.smtp.auth"));
    if (isAuth) {
      session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(properties.getUsername(), properties.getPassword());
        }
      });
    }
    MimeMessage mimeMessage = new MimeMessage(session);
    MimeMultipart multipart = new MimeMultipart();
    MimeBodyPart body = new MimeBodyPart();
    body.setText(this.getMailBody(mailModel));
    multipart.addBodyPart(body);
    mimeMessage.setFrom(mailModel.getFrom());
    mimeMessage.setSubject(mailModel.getSubject());
    if (CollectionUtils.isNotEmpty(mailModel.getTo())) {
      mailModel.getTo().forEach(address -> {
        try {
          mimeMessage.addRecipient(RecipientType.TO, address);
        } catch (MessagingException e) {
          log.error(ExceptionUtils.getStackTrace(e));
        }
      });
    }
    if (CollectionUtils.isNotEmpty(mailModel.getCc())) {
      mailModel.getTo().forEach(address -> {
        try {
          mimeMessage.addRecipient(RecipientType.CC, address);
        } catch (MessagingException e) {
          log.error(ExceptionUtils.getStackTrace(e));
        }
      });
    }
    if (CollectionUtils.isNotEmpty(mailModel.getBcc())) {
      mailModel.getTo().forEach(address -> {
        try {
          mimeMessage.addRecipient(RecipientType.BCC, address);
        } catch (MessagingException e) {
          log.error(ExceptionUtils.getStackTrace(e));
        }
      });
    }
    if (CollectionUtils.isNotEmpty(mailModel.getAttachments())) {
      mailModel.getAttachments().forEach(attachment -> {
        if (attachment.getBaos() != null) {
          try (ByteArrayInputStream bais = new ByteArrayInputStream(attachment.getBaos().toByteArray())) {
            MimeBodyPart bodyPart = new MimeBodyPart();
            DataSource ds = new ByteArrayDataSource(bais, attachment.getContentType());
            bodyPart.setDataHandler(new DataHandler(ds));
            bodyPart.setFileName(attachment.getFileName());
            multipart.addBodyPart(bodyPart);
          } catch (IOException | MessagingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
          }
        }
      });
    }
    mimeMessage.setContent(multipart);
    return mimeMessage;
  }

  /**
   * Thymeleafテンプレートを使用したメール本文を取得する
   * 
   * @param mailModel メールモデル
   * @return メール本文
   */
  private String getMailBody(MailModel mailModel) {
    if (ObjectUtils.allNull(mailModel.getTemplatePath(), mailModel.getBodyParams(), mailModel.getBody())) {
      throw new IllegalArgumentException("メールテンプレートパス、メールボディマップ、テキストボディの全てが空です");
    }

    if (StringUtils.isNotEmpty(mailModel.getTemplatePath())) {
      ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
      templateResolver.setTemplateMode(TemplateMode.TEXT);
      templateResolver.setCharacterEncoding(mailModel.getCharset());

      SpringTemplateEngine engine = new SpringTemplateEngine();
      Context context = new Context();
      context.setVariables(mailModel.getBodyParams());
      engine.setTemplateResolver(templateResolver);
      return engine.process(mailModel.getTemplatePath(), context);
    } else {
      String body = mailModel.getBody();
      log.debug("mail body:" + StringUtils.defaultString(body, ""));
      if (StringUtils.isNotEmpty(body) && MapUtils.isNotEmpty(mailModel.getBodyParams())) {
        List<String> params = mailModel.getBodyParams().keySet().stream().map(key -> {
          return key.toString() + "=" + mailModel.getBodyParams().get(key);
        }).collect(Collectors.toList());
        log.debug("mail body param map:" + String.join(", ", params));

        final String format = mailModel.getParamFormat();
        log.debug("param format:" + format);
        for (Map.Entry<String, Object> entry : mailModel.getBodyParams().entrySet()) {
          final String key = entry.getKey();
          final String value = Objects.toString(entry.getValue(), "");
          final String regex = String.format(format, key);
          log.debug("replace param regex:" + regex);
          body = body.replaceAll(regex, value);
        }
        log.debug("replaced mail body:" + StringUtils.defaultString(body, ""));
      }
      return body;
    }
  }

  /**
   * msgファイル形式のデータを作成しOutputStreamに書き込む
   * 
   * @param mailModel メールモデル
   * @param os        OutputStream
   * @throws IOException
   */
  public void createMsgFile(MailModel mailModel, OutputStream os) throws IOException {
    OutlookMessage msg = new OutlookMessage();
    msg.setFrom(mailModel.getFrom().getAddress());
    msg.setSubject(mailModel.getSubject());
    msg.setPlainTextBody(this.getMailBody(mailModel));
    if (CollectionUtils.isNotEmpty(mailModel.getTo())) {
      mailModel.getTo().forEach(address -> {
        msg.addRecipient(Type.TO, address.getAddress(), address.getPersonal());
      });
    }
    if (CollectionUtils.isNotEmpty(mailModel.getCc())) {
      mailModel.getTo().forEach(address -> {
        msg.addRecipient(Type.CC, address.getAddress(), address.getPersonal());
      });
    }
    if (CollectionUtils.isNotEmpty(mailModel.getBcc())) {
      mailModel.getTo().forEach(address -> {
        msg.addRecipient(Type.BCC, address.getAddress(), address.getPersonal());
      });
    }
    if (CollectionUtils.isNotEmpty(mailModel.getAttachments())) {
      mailModel.getAttachments().forEach(attachment -> {
        if (attachment.getBaos() != null) {
          try (ByteArrayInputStream bais = new ByteArrayInputStream(attachment.getBaos().toByteArray())) {
            msg.addAttachment(attachment.getFileName(), attachment.getContentType(), bais);
          } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
          }
        }
      });
    }
    msg.writeTo(os);
  }

  /**
   * メールを送信する
   * 
   * @param mailModel メールモデル
   * @throws MessagingException
   */
  public void send(MailModel mailModel) throws MessagingException {
    MimeMessage mimeMessage = this.createMimeMessage(mailModel);
    Transport.send(mimeMessage);
  }
}
