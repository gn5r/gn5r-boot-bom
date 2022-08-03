package com.github.gn5r.boot.mail.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import com.github.gn5r.boot.mail.model.MailAttachmentModel;
import com.github.gn5r.boot.mail.model.MailModel;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Import({ MailSenderAutoConfiguration.class })
public class MailServiceTest {

  private static final String MAIL_TEMPLATE = "template.txt";
  private static final String EML_FILE_PATH = "C:/Users/gn5r/Desktop/添付ファイルなしemlファイル.eml";

  @Autowired
  private MailProperties properties;

  @InjectMocks
  private MailService mailService;

  @BeforeEach
  public void init() {
    ReflectionTestUtils.setField(mailService, "properties", properties);
    MockitoAnnotations.openMocks(this);
  }

  private MailModel getTestData() {
    MailModel mailModel = new MailModel();
    mailModel.setSubject("createEmlDataTestNotFile");
    mailModel.setFrom("shangyuan.tuolang@outlook.com", "from");
    mailModel.addTo("via.gn5r@gmail.com", "to1");
    mailModel.addCc("shangyuan.tuolang@gmail.com", "cc1");
    Map<String, Object> body = Maps.newHashMap();
    body.put("month", "3");
    body.put("hour", "134");
    mailModel.setBodyParams(body);
    mailModel.setTemplatePath(MAIL_TEMPLATE);
    return mailModel;
  }

  @Test
  public void sendMail() {
    try {
      MailModel mailModel = this.getTestData();
      mailService.send(mailModel);
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
      fail(e);
    }
  }

  @Test
  public void createEmlDataTestNotFile() {
    try (FileOutputStream fos = new FileOutputStream(EML_FILE_PATH)) {
      log.debug("テストを開始します");
      MailModel mailModel = this.getTestData();
      mailService.createEmlFile(mailModel, fos);
      log.debug("テストが正常に終了しました");
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
      fail(e);
    }
  }

  @Test
  public void createEmlDataTestWithFile() {
    try (FileOutputStream fos = new FileOutputStream("C:/Users/gn5r/Desktop/添付ファイルあり.eml");
        FileInputStream fis = new FileInputStream(EML_FILE_PATH);
        ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      log.debug("テストを開始します");
      MailModel mailModel = this.getTestData();
      fis.transferTo(baos);
      MailAttachmentModel attachment = new MailAttachmentModel();
      attachment.setFileName("mail.eml");
      attachment.setContentType(MediaType.TEXT_PLAIN_VALUE);
      attachment.setBaos(baos);
      mailModel.addAttachment(attachment);
      mailService.createEmlFile(mailModel, fos);
      log.debug("テストが正常に終了しました");
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
      fail(e);
    }
  }

  @Test
  public void testGetMailTextBody() {
    try {
      Method method = mailService.getClass().getDeclaredMethod("getMailBody", MailModel.class);
      method.setAccessible(true);
      MailModel mailModel = new MailModel();
      mailModel.setBody("あいうえお\n改行もできる\n変数は$で囲む。TEST=$TEST$、TEST2=$TEST2$");
      mailModel.setBodyParams(new HashMap<String, Object>() {
        {
          put("TEST", "疑似変数");
          put("TEST2", "疑似変数2");
        }
      });
      String body = (String) method.invoke(mailService, mailModel);
      assertTrue(StringUtils.isNotEmpty(body));
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
      fail(e);
    }
  }

  @Test
  public void testGetMailTextBodyWithChangedPattern() {
    try {
      Method method = mailService.getClass().getDeclaredMethod("getMailBody", MailModel.class);
      method.setAccessible(true);
      MailModel mailModel = new MailModel();
      mailModel.setBody("あいうえお\n改行もできる\n変数は$で囲む。TEST=${TEST}");
      mailModel.setParamFormat("\\$\\{%S\\}");
      mailModel.setBodyParams(new HashMap<String, Object>() {
        {
          put("TEST", "疑似変数");
        }
      });
      String body = (String) method.invoke(mailService, mailModel);
      assertTrue(StringUtils.isNotEmpty(body));
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
      fail(e);
    }
  }

  @Test
  public void testSendTextBodyMail() {
    try {
      MailModel mailModel = new MailModel();
      mailModel.setFrom("shangyuan.tuolang@outlook.com", "shangyuan.tuolang");
      mailModel.addTo("via.gn5r@gmail.com", "via.gn5r");
      mailModel.setSubject("テキストボディのテストメール");
      mailModel.setBody("テストです。\r\n改行は出来ているのか？\r\n変数は$で囲む。TEST=$TEST$");
      mailModel.setBodyParams(new HashMap<String, Object>() {
        {
          put("TEST", "疑似変数");
        }
      });
      mailService.send(mailModel);
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
      fail(e);
    }
  }

  @Configuration
  static class LocalTestContext {
  }
}
