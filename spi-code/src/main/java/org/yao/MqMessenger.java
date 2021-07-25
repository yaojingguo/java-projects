package org.yao;

public class MqMessenger implements  Messenger {

  @Override
  public void sendMessage(final String message) {
    System.out.printf("push with message %s\n", message);
  }
}
