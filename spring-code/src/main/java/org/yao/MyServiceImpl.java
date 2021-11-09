package org.yao;

public class MyServiceImpl implements MyService {

  private Prefix prefix;

  public MyServiceImpl(Prefix prefix) {
    this.prefix = prefix;
  }

  @Override
  public void doStuff() {
    System.out.printf(prefix.prefix() + "do some stuff\n");
  }
}
