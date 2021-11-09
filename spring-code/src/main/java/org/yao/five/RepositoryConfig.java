package org.yao.five;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yao.five.AccountRepository;
import org.yao.five.JdbcAccountRepository;

@Configuration
public class RepositoryConfig {

  @Bean
  public AccountRepository accountRepository(DataSource dataSource) {
    return new JdbcAccountRepository(dataSource);
  }
}
