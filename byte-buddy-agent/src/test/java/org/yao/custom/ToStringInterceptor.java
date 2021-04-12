package org.yao.custom;

public class ToStringInterceptor {
  public static String makeString(@StringValue("Hello!") String value) {
    return value;
  }
}