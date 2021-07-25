package org.yao;

public class EmailMessenger implements Messenger {
  @Override
  public void sendMessage(final String message) {
    System.out.printf("send email with message %s\n", message);
  }
}
