package org.yao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bar {
  private Foo f;

  @Autowired
  public void set(Foo f) {
    this.f = f;
    System.out.printf("set Foo");
  }
}
