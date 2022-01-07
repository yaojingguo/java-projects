package com.example.actuatorservice;

import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.MicrometerConsumerListener;
import org.springframework.kafka.core.MicrometerProducerListener;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class Config {
  public static final String topicName = "apiTopic";
  public final static String myTopic = "myTopic";

  @Autowired private MeterRegistry meterRegistry;

  // Consume
  @Bean
  ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
      ConsumerFactory<String, String> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }

  @Bean
  public ConsumerFactory<String, String> consumerFactory(MeterRegistry meterRegistry) {
    DefaultKafkaConsumerFactory<String, String> cf =
        new DefaultKafkaConsumerFactory<>(consumerProps());
    cf.addListener(
        new MicrometerConsumerListener<String, String>(
            meterRegistry,
            Collections.singletonList(new ImmutableTag("customTag", "customTagValue"))));
    return cf;
  }

  private Map<String, Object> consumerProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "group");
//    props.put("client.id", "mixed");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    return props;
  }

  @Bean
  public Listener listener() {
    return new Listener();
  }

  // Produce
  @Bean
  public Sender sender(KafkaTemplate<String, String> template) {
    return new Sender(template);
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate(
      ProducerFactory<String, String> producerFactory) {
    return new KafkaTemplate<String, String>(producerFactory);
  }

//  @Bean
//  public ProducerFactory<String, String> producerFactory(MeterRegistry meterRegistry) {
//    DefaultKafkaProducerFactory<String, String> pf =
//        new DefaultKafkaProducerFactory<>(senderProps());
//    pf.addListener(
//        new MicrometerProducerListener<String, String>(
//            meterRegistry,
//            Collections.singletonList(new ImmutableTag("customTag", "customTagValue"))));
//    return pf;
//  }

  private Map<String, Object> senderProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return props;
  }
}
