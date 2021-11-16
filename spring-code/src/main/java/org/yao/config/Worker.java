package org.yao.config;

import org.springframework.beans.factory.annotation.Value;

public class Worker {

  @Value("${codename}")
  private String codename;

  @Value("${name}")
  private String name;

  public void show() {
    System.out.printf("name: %s, codename: %s\n", name, codename);
  }

}
