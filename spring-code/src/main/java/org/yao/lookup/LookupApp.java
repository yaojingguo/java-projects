package org.yao.lookup;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class LookupApp {
  public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(LookupApp.class);
    CommandManager mgr = ctx.getBean(CommandManager.class);
    Object obj = new Object();
    System.out.println(mgr.process(obj));
    System.out.println(mgr.process(obj));

  }
}
