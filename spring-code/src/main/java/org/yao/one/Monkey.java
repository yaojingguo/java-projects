package org.yao.one;

public class Monkey {
  private String name;

  public Monkey(String name) {
    System.out.printf("created monkey %s\n", name);
    this.name = name;
  }

  @Override
  public String toString() {
    return "monkey: " + name;
  }
}
