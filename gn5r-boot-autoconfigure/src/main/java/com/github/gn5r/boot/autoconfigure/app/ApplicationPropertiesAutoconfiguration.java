package com.github.gn5r.boot.autoconfigure.app;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ ApplicationProperties.class })
public class ApplicationPropertiesAutoconfiguration {

  @Bean
  public ApplicationProperties applicationProperties() {
    return new ApplicationProperties();
  }
}
