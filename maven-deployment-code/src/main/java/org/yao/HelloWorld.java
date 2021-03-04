package org.yao;

public class HelloWorld {
  public static void main(String[] args) {
    greet();
  }

  public static void greet() {
    System.out.println(sentence());    
  }

  public static String sentence() {
    return "hello, world";
  }
}
