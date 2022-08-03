package com.github.gn5r.boot.autoconfigure.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;

import com.github.gn5r.boot.mail.service.MailService;

@ConditionalOnClass({ MailProperties.class })
@ConditionalOnMissingBean({ MailService.class })
@ConditionalOnProperty(prefix = "spring.mail", name = "host")
public class MailServiceAutoConfigure {

  @Bean
  public MailService mailService() {
    return new MailService();
  }
}
