package com.github.gn5r.boot.autoconfigure.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnClass({ EnableSwagger2.class })
@ConditionalOnProperty(prefix = "swagger", value = { "base-package" })
@EnableConfigurationProperties({ SwaggerProperties.class })
public class SwaggerAutoConfiguration {

  @Autowired
  private SwaggerProperties properties;

  @Bean
  public SwaggerConfig swaggerConfig() {
    return new SwaggerConfig();
  }

  @EnableSwagger2
  public class SwaggerConfig {

    @Bean
    public Docket docket() {
      return new Docket(DocumentationType.SWAGGER_2).select()
          .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage())).paths(PathSelectors.any()).build()
          .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
      return new ApiInfoBuilder().title(properties.getUi().getTitle())
          .description(properties.getUi().getDescription())
          .build();
    }
  }
}
