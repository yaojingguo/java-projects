package com.example.actuatorservice;

import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class Foo {
  public Foo() {
    System.out.printf("constructing foo\n");
  }
}
