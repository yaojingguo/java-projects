package com.example.actuatorservice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {
  private Logger log = LoggerFactory.getLogger(getClass());

  @KafkaListener(id = "listen-a", topics = Config.topicName)
  public void listen1(ConsumerRecord<String, String> record) {
    log.info(
        "topic: {}, partition: {}, offset: {}, value: {}",
        record.topic(),
        record.partition(),
        record.offset(),
        record.value());
  }

//  @KafkaListener(id = "listen-b", topics = Config.myTopic)
//  public void listen2(ConsumerRecord<String, String> record) {
//    log.info(
//        "topic: {}, partition: {}, offset: {}, value: {}",
//        record.topic(),
//        record.partition(),
//        record.offset(),
//        record.value());
//  }
}
