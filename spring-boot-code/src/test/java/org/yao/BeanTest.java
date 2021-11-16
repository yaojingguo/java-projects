package org.yao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeanTest {

  @Autowired
  private Teacher b;

  @Autowired
  private BookController controller;

  @Test
  public void contextLoads() {
    System.out.printf("context loaded\n");
    System.out.println(b);
    System.out.println(controller);
  }


}
