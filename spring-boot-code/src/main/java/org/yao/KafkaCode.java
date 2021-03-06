package org.yao;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

public class KafkaCode {
  private static String topic = "two";

  public static void main(String[] args) throws Exception {
//    nativeSend();
//    nativeSendCallback();
//    templateSend();
//    templateSendCallback();

    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "foo");
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    props.put("auto.offset.reset", "earliest");
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

    Thread.sleep(20 * 1000);
  }

  private static void nativeSend() throws Exception {
    KafkaProducer<String, String> producer = new KafkaProducer<String, String>(configMap());
    ProducerRecord<String, String> record = new ProducerRecord<>(topic, "native-send-msg");
    Future<RecordMetadata> result = producer.send(record);
    result.get();
    System.out.println("sent by native API");
  }

  private static void nativeSendCallback() throws Exception {
    KafkaProducer<String, String> producer = new KafkaProducer<String, String>(configMap());
    ProducerRecord<String, String> record = new ProducerRecord<>(topic, "native-send-callback-msg");
    Future<RecordMetadata> result =
        producer.send(record, (RecordMetadata recordMetadata, Exception e) -> {
          if (e == null) {
            System.out.println("sending succeeded");
          } else {
            System.out.println("sending failed");
          }
        });
    result.get();
    System.out.println("sent by native API");
  }

  public static void templateSendCallback() throws Exception {
    DefaultKafkaProducerFactory factory = new DefaultKafkaProducerFactory<>(configMap());
    KafkaTemplate<String, String> template = new KafkaTemplate<>(factory, true);

    final CountDownLatch latch = new CountDownLatch(1);
    int resultCode =
        template.execute(
            (Producer<String, String> producer) -> {
              producer.send(
                  new ProducerRecord<>(topic, "kafka-template-callback-msg"),
                  (RecordMetadata metadata, Exception e) -> {
                    if (e != null) {
                      System.out.printf("sending failed");
                    } else {
                      System.out.printf("sending succeeded");
                    }
                    latch.countDown();
                  });
              return 0;
            });
    latch.await();
  }

  public static void templateSend() throws Exception {
    DefaultKafkaProducerFactory factory = new DefaultKafkaProducerFactory<>(configMap());
    KafkaTemplate<String, String> template = new KafkaTemplate<>(factory, true);

    template.send(topic, "kafka-template-msg").get();
    System.out.println("send by kafka template");
  }

  private static Map<String, Object> configMap() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
    config.put(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringSerializer");
    config.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringSerializer");
    return config;
  }
}
