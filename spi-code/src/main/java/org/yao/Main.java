package org.yao;

import java.util.ServiceLoader;

public class Main {
  public static void main(String[] args) {
    ServiceLoader<Messenger> serviceLoader = ServiceLoader.load(Messenger.class);

    for (Messenger service : serviceLoader) {
      service.sendMessage("Hello");
    }
  }
}
