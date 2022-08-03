package com.github.gn5r.boot.autoconfigure.doma;

import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "doma", value = "dialect")
@ConditionalOnClass({ Config.class })
@ConditionalOnMissingBean({ Config.class })
@Configuration
public class AppConfig implements Config {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private Dialect dialect;

  @Override
  public DataSource getDataSource() {
    return dataSource;
  }

  @Override
  public Dialect getDialect() {
    return dialect;
  }
}
