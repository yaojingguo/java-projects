package org.yao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
// Make spring scan components in com.yao and org.yao base packages.
// @ComponentScan(basePackages = {"com.yao", "org.yao"})
@SpringBootApplication
public class RestServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestServiceApplication.class, args);
  }
}
