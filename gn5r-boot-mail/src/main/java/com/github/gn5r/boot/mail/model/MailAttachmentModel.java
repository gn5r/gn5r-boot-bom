package com.github.gn5r.boot.mail.model;

import java.io.ByteArrayOutputStream;

import lombok.Data;

@Data
public class MailAttachmentModel {

  private String fileName;
  private String contentType;
  private ByteArrayOutputStream baos;
}
