package org.yao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public MyService myService(Prefix prefix) {
    return new MyServiceImpl(prefix);
  }

  @Bean
  public Prefix prefix() {
    return new Prefix();
  }
}
