package org.yao.method;

/**
 * Class to be instrumented.
 */
public class Foo {
  public void execute() {
    System.out.println("a method whose name will be changed");
  }

  public void changeMethodContent() {
    System.out.println("a method whose behaviour will be changed");
  }
}
