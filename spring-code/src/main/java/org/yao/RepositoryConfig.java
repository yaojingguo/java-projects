package org.yao;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

  @Bean
  public AccountRepository accountRepository(DataSource dataSource) {
    return new JdbcAccountRepository(dataSource);
  }
}