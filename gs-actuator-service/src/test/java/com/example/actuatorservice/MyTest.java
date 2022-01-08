package com.example.actuatorservice;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class MyTest {
  @Test
  public void testSupplier() {
    Supplier<Integer> value = () -> 10;
    System.out.printf("value: %d\n", value.get());
  }
}
