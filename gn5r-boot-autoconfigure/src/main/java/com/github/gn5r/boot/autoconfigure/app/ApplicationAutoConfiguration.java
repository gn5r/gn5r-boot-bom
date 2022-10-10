package com.github.gn5r.boot.autoconfigure.app;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ ApplicationProperties.class })
public class ApplicationAutoConfiguration {
}
