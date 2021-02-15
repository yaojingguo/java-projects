package com.yao.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner3 implements CommandLineRunner {
  @Override
  public void run(String... args) throws Exception {
    System.out.println("Hi, I am runner3.");
  }

}