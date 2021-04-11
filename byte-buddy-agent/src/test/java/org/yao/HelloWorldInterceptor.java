package org.yao;

public class HelloWorldInterceptor implements Interceptor {
  @Override
  public String doSomethingElse() {
    return "Hello World!";
  }
}
