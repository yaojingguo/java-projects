package com.example.actuatorservice;

import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

  private final KafkaTemplate<String, String> template;

  public Sender(KafkaTemplate<String, String> template) {
    this.template = template;
  }

  public void send(String topic, String key, String val) {
    this.template.send(topic, key, val);
  }
}
