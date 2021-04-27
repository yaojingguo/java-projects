package org.yao;

import org.apache.logging.log4j.message.Message;

public class MyMessage implements Message {
  private String message;
  private String[] params;
  private Throwable t;


  public static MyMessage m(String message, String... params) {
    return new MyMessage(message, params);
  }

  public MyMessage(String message, String... params) {
    this.message = message;
    this.params = params;
  }

  public static MyMessage m(String message, Throwable t, String... params) {
    return new MyMessage(message,t, params);
  }

  public MyMessage(String message,Throwable t,  String... params) {
    this.message = message;
    this.t = t;
    this.params = params;
  }

  @Override
  public String getFormattedMessage() {
    StringBuilder sb = new StringBuilder(message);
    for (int i = 0; i < params.length; i +=2) {
      sb.append(" ");
      sb.append(params[i]);
      sb.append("=");
      sb.append(params[i+1]);
    }
    return sb.toString();
  }

  @Override
  public String getFormat() {
    return null;
  }

  @Override
  public Object[] getParameters() {
    return params;
  }

  @Override
  public Throwable getThrowable() {
    return t;
  }
}

