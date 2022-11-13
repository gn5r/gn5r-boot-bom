package com.github.gn5r.boot.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import com.github.gn5r.boot.autoconfigure.modelmapper.ModelMapperAutoConfiguration;

public class ModelMapperAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(ModelMapperAutoConfiguration.class));

  @Test
  public void registeredBeans() {
    this.contextRunner.run(context -> {
      assertThat(context).hasSingleBean(ModelMapper.class);
    });
  }
}