package org.yao.five;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

  @Bean
  public TransferService transferService(AccountRepository accountRepository) {
    return new TransferServiceImpl(accountRepository);
  }
}