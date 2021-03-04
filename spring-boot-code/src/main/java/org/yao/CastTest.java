package org.yao;

public class CastTest {

  public static void main(String[] args) {
    convert(null);
  }

  public static void convert(Object o) {
    Foo f = (Foo) o;
    System.out.printf("object: %s\n", f);
  }

  public static class Foo {}
}
