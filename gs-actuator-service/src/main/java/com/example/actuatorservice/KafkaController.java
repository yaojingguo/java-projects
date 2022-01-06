package com.example.actuatorservice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@RestController
@RequestMapping("kafka")
public class KafkaController {
  private static String topicName = "apiTopic";

  @GetMapping("start")
  public void start() {
    new Thread(() -> {
      verifyAutoCommit();
    }).start();
    System.out.printf("thread started\n");
  }

  private static void nap(long millis) {
    try {
      System.out.printf("napping for %d milliseconds\n", millis);
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private static KafkaConsumer<String, String> createConsumer() {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "foo");
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    props.put("auto.offset.reset", "earliest");
    return new KafkaConsumer<>(props);
  }

  private static void verifyAutoCommit() {
    try (KafkaConsumer<String, String> consumer = createConsumer(); ) {
      consumer.subscribe(Arrays.asList(topicName));
//      nap(1000 * 300);
      while (true) {
        long seconds = 2;
        System.out.printf("polling for %d seconds\n", seconds);
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(seconds));
        System.out.printf("polled %d records\n", records.count());
        for (ConsumerRecord<String, String> record : records) {
          System.out.println(record.offset() + ": " + record.value());
        }
      }
    }
  }
}
