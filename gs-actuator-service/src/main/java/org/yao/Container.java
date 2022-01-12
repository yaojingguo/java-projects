package org.yao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class Container {
  private static Logger log = LoggerFactory.getLogger(Container.class);

  private static KafkaTemplate<String, String> one;
  private static KafkaTemplate<String, String> two;

  public static synchronized void setOne(KafkaTemplate<String, String> one) {
    Container.one = one;
  }

  public static synchronized void setTwo(KafkaTemplate<String, String> two) {
    Container.two = two;
  }

  public static synchronized void check() {
    if (Container.one == Container.two) {
      log.info("same instance");
    } else {
      log.info("different instances");
    }
  }
}
