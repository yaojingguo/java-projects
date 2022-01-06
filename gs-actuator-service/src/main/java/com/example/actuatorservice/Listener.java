package com.example.actuatorservice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {
  private Logger log = LoggerFactory.getLogger(getClass());

  @KafkaListener(id = "listen1", topics = Config.topicName)
  public void listen(ConsumerRecord<String, String> record) {
    log.info(
        "topic: {}, partition: {}, offset: {}, value: {}",
        record.topic(),
        record.partition(),
        record.offset(),
        record.value());
  }
}
