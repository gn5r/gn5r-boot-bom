package com.github.gn5r.boot.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import com.github.gn5r.boot.autoconfigure.app.ApplicationAutoConfiguration;
import com.github.gn5r.boot.autoconfigure.app.ApplicationProperties;

public class ApplicationAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(ApplicationAutoConfiguration.class));

  @Test
  public void registeredBeans() {
    this.contextRunner.run(context -> {
      assertThat(context).hasSingleBean(ApplicationProperties.class);
      assertThat(context.getBean(ApplicationProperties.class).isDebug()).isFalse();
    });
  }
}