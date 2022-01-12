package org.yao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Greeting.class)
public class Boot {

  private Logger log = LogManager.getLogger();

  @Bean(initMethod = "init")
  @ConditionalOnMissingBean
  public Greeting greet() {
    Greeting o = new Greeting(1, "Nihao");
    log.info("created a greeting");
    return o;
  }

}
