package org.yao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {
  public static void main(String[] args) throws Exception {
    SpringApplication.run(RestServiceApplication.class, args);
    for (int i = 0; i < 10; i++) {
      Thread.sleep(1000);
      System.out.printf("i: %d\n", i);
    }
  }
}
