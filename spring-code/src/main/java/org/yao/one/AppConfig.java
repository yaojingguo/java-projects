package org.yao.one;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppConfig {

  @Bean
  public MyService myService(Prefix prefix) {
    return new MyServiceImpl(prefix);
  }

  @Bean(initMethod = "init")
  public Prefix prefix() {
    return new Prefix();
  }

  @Bean
  public Monkey m1() {
    return new Monkey("xiaoyu");
  }

  @Bean
  public Monkey m2() {
    return new Monkey("xiaoyue");
  }
}
