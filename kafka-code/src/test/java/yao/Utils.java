package yao;

import java.util.Properties;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class Utils {
  public static Properties producerConfig(String clientId) {
    Properties cfg = new Properties();
    cfg.put("client.id", clientId);
    cfg.put("bootstrap.servers", "localhost:9092");
    cfg.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    cfg.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    return cfg;
  }

  public static KafkaProducer<String, String> createProducer(String clientId) {
    return new KafkaProducer<>(producerConfig(clientId));
  }

  public static Properties consumerConfig(String groupId, String clientId) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "foo");
    props.put("client.id", clientId);
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    props.put("auto.offset.reset", "earliest");
    return props;
  }

  public static KafkaConsumer<String, String> createConsumer(String groupId, String clientId) {
    return new KafkaConsumer<>(consumerConfig(groupId, clientId));
  }
}
