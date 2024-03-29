package org.yao.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Content in two.yml overrides content in one.yml.
 */
@Configuration
@PropertySource(value={"classpath:one.yml", "classpath:two.yml"})
public class Main {

  public static void main(String[] args) throws Exception {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
    Worker w = ctx.getBean(Worker.class);
    w.show();
    System.out.printf("hello, world\n");
    for (int i = 0; i < 10; i++) {
      Thread.sleep(1000);
      System.out.printf("i: %d\n", i + 1);
    }
  }

  @Bean
  public Worker worker() {
    return new Worker();
  }
}
