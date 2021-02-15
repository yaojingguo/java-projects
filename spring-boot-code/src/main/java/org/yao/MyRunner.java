package org.yao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

  @Value("${no:0}")
  private int no;

  @Value("${name:XXX}")
  private String name;

  @Override
  public void run(String... args) throws Exception {
    System.out.printf("no: %d, name : %s\n", no, name);
  }

}
