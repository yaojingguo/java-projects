package com.example.actuatorservice;

import org.springframework.stereotype.Component;

@Component
public class Foo {
  public Foo() {
    System.out.printf("constructing foo\n");
  }
}
