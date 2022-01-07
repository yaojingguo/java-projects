package com.example.actuatorservice;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.kafka.KafkaClientMetrics;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@RestController
@RequestMapping("kafka")
public class KafkaController {

  @Autowired private MeterRegistry meterRegistry;

  @GetMapping("start")
  public void start() {
    new Thread(
            () -> {
              kafkaApi();
            })
        .start();
    System.out.printf("thread started\n");
  }

  private void nap(long millis) {
    try {
      System.out.printf("napping for %d milliseconds\n", millis);
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private KafkaConsumer<String, String> createConsumer() {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "foo");
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    props.put("auto.offset.reset", "earliest");
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

    KafkaClientMetrics metrics = new KafkaClientMetrics(consumer);
    metrics.bindTo(meterRegistry);

    return new KafkaConsumer<>(props);
  }

  private void kafkaApi() {
    try (KafkaConsumer<String, String> consumer = createConsumer(); ) {
      consumer.subscribe(Arrays.asList(Config.topicName));
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
