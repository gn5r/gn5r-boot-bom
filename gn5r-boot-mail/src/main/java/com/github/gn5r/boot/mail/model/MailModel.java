package com.github.gn5r.boot.mail.model;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MailModel {

  private String charset;
  private String subject;
  private InternetAddress from;
  private List<InternetAddress> to;
  private List<InternetAddress> cc;
  private List<InternetAddress> bcc;
  private String templatePath;
  private String body;
  private String paramFormat;
  private Map<String, Object> bodyParams;
  private List<MailAttachmentModel> attachments;

  public MailModel() {
    this.to = Lists.newArrayList();
    this.cc = Lists.newArrayList();
    this.bcc = Lists.newArrayList();
    this.paramFormat = "\\$%S\\$";
    this.bodyParams = Maps.newHashMap();
    this.attachments = Lists.newArrayList();
    this.charset = "UTF-8";
  }

  public MailModel(String charset) {
    this();
    this.charset = charset;
  }

  public void setFrom(String address) {
    try {
      this.from = new InternetAddress(address);
    } catch (AddressException e) {
      log.error(ExceptionUtils.getStackTrace(e));
    }
  }

  public void setFrom(String address, String name) {
    try {
      this.from = new InternetAddress(address, name);
    } catch (UnsupportedEncodingException e) {
      log.error(ExceptionUtils.getStackTrace(e));
    }
  }

  public void addTo(String address) {
    try {
      this.to.add(new InternetAddress(address));
    } catch (AddressException e) {
      log.error(ExceptionUtils.getStackTrace(e));
    }
  }

  public void addTo(String address, String name) {
    try {
      this.to.add(new InternetAddress(address, name));
    } catch (UnsupportedEncodingException e) {
      log.error(ExceptionUtils.getStackTrace(e));
    }
  }

  public void setTo(List<InternetAddress> to) {
    this.to = to;
  }

  public void addCc(String address) {
    try {
      this.cc.add(new InternetAddress(address));
    } catch (AddressException e) {
      log.error(ExceptionUtils.getStackTrace(e));
    }
  }

  public void addCc(String address, String name) {
    try {
      this.cc.add(new InternetAddress(address, name));
    } catch (UnsupportedEncodingException e) {
      log.error(ExceptionUtils.getStackTrace(e));
    }
  }

  public void addBcc(String address) {
    try {
      this.bcc.add(new InternetAddress(address));
    } catch (AddressException e) {
      log.error(ExceptionUtils.getStackTrace(e));
    }
  }

  public void addBcc(String address, String name) {
    try {
      this.bcc.add(new InternetAddress(address, name));
    } catch (UnsupportedEncodingException e) {
      log.error(ExceptionUtils.getStackTrace(e));
    }
  }

  public void addAttachment(MailAttachmentModel attachmentModel) {
    this.attachments.add(attachmentModel);
  }
}
