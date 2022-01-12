package org.yao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("another")
public class AnotherController {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private KafkaTemplate<String, String> template2;

  @GetMapping("send")
  public void send() {
    try {
      Container.setTwo(template2);
      Container.check();
      template2.send(Config.topicName, "two").get();
      log.info("{} sent message", template2);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
