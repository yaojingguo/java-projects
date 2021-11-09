package org.yao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigA {
  @Bean
  public A aa() {
    return new A();
  }
}
