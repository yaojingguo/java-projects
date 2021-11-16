package org.yao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeanTest {

  @Autowired
  private Teacher b;

  @Test
  public void contextLoads() {
    System.out.printf("context loaded\n");
    System.out.println(b);
  }

  @Test
  public void testBean() {
    System.out.printf("teaching result: %s\n", b.teach());
  }

}
