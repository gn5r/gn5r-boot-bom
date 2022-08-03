package com.github.gn5r.boot.commons.generator;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class FQCNBeanNameGenerator extends AnnotationBeanNameGenerator {
  @Override
  protected String buildDefaultBeanName(BeanDefinition definition) {
    return definition.getBeanClassName();
  }
}
