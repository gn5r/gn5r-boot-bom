package com.github.gn5r.boot.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import com.github.gn5r.boot.autoconfigure.swagger.SwaggerAutoConfiguration;
import com.github.gn5r.boot.autoconfigure.swagger.SwaggerProperties;
import com.github.gn5r.boot.autoconfigure.swagger.SwaggerAutoConfiguration.SwaggerConfig;

import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(SwaggerAutoConfiguration.class));

  @Test
  public void notRegisteredBeans() {
    this.contextRunner.run(context -> {
      assertThat(context).doesNotHaveBean(SwaggerProperties.class);
      assertThat(context).doesNotHaveBean(SwaggerConfig.class);
    });
  }

  @Test
  public void registeredBeansByWithBasePackageValue() {
    this.contextRunner.withPropertyValues("swagger.base-package=com.github.gn5r").run(context -> {
      assertThat(context).hasSingleBean(SwaggerProperties.class);
      assertThat(context.getBean(SwaggerProperties.class).getBasePackage()).isEqualTo("com.github.gn5r");
      assertThat(context).hasSingleBean(Docket.class);
    });
  }

  @Test
  public void registeredBeansByWithBasePackageValue2() {
    this.contextRunner.withPropertyValues("swagger.basePackage=com.github.gn5r").run(context -> {
      assertThat(context).hasSingleBean(SwaggerProperties.class);
      assertThat(context.getBean(SwaggerProperties.class).getBasePackage()).isEqualTo("com.github.gn5r");
      assertThat(context).hasSingleBean(Docket.class);
    });
  }

  @Test
  public void testProperties() {
    this.contextRunner.withPropertyValues("swagger.base-package=com.github.gn5r", "swagger.ui.title=テストタイトル", "swagger.ui.description=テスト説明").run(context -> {
      assertThat(context).hasSingleBean(SwaggerProperties.class);
      assertThat(context.getBean(SwaggerProperties.class).getBasePackage()).isEqualTo("com.github.gn5r");
      assertThat(context.getBean(SwaggerProperties.class).getUi()).isNotNull();
      assertThat(context.getBean(SwaggerProperties.class).getUi().getTitle()).isEqualTo("テストタイトル");
      assertThat(context.getBean(SwaggerProperties.class).getUi().getDescription()).isEqualTo("テスト説明");
    });
  }
}
