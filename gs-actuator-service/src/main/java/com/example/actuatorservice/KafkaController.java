package com.example.actuatorservice;

import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.kafka.KafkaClientMetrics;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("kafka")
public class KafkaController {

  private static Logger log = LoggerFactory.getLogger(KafkaController.class);

  @Autowired private MeterRegistry meterRegistry;




  @GetMapping("start")
  public void start() {
    new Thread(
            () -> {
              kafkaApi();
            })
        .start();
    log.info("thread started");
  }

  private void nap(long millis) {
    try {
      log.info("napping for {} milliseconds", millis);
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private KafkaConsumer<String, String> createConsumer() {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "bar-group");
    props.put("client.id", "bar");
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    props.put("auto.offset.reset", "earliest");

    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    List<Tag> consumerTags = new ArrayList<>();
    consumerTags.add(new ImmutableTag("student.no", "9"));
    KafkaClientMetrics metrics = new KafkaClientMetrics(consumer, consumerTags);
    metrics.bindTo(meterRegistry);
    log.info("bound {} to {}", metrics, meterRegistry);

    return consumer;
  }

  private void kafkaApi() {
    try (KafkaConsumer<String, String> consumer = createConsumer(); ) {
      consumer.subscribe(Arrays.asList(Config.myTopic));
      //      nap(1000 * 300);
      while (true) {
        long seconds = 2;
        log.info("polling for {} seconds", seconds);
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(seconds));
        log.info("polled {} records", records.count());
        for (ConsumerRecord<String, String> record : records) {
          log.info("offset: {}, value: {}", record.offset(), record.value());
        }
      }
    }
  }
}
