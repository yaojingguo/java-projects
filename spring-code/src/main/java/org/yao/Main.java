package org.yao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {
    oneWay();
  }

  private static void twoWay() {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(MyServiceImpl.class);
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
  }

  private static void oneWay() {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
  }

  private static void threeWay() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    ctx.register(MyServiceImpl.class);
    ctx.refresh();
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
  }
}
