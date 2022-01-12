package org.yao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("template")
public class TemplateController {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private KafkaTemplate<String, String> template;

  @GetMapping("send")
  public void send() {
    try {
      Container.setOne(template);
      template.send(Config.topicName, "one").get();
      log.info("template {} sent message", template);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
