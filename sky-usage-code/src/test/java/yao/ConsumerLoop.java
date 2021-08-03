package yao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

public class ConsumerLoop implements Runnable {
  private final KafkaConsumer<String, String> consumer;
  private final List<String> topics;
  private final String id;

  public ConsumerLoop(String id, String groupId, List<String> topics) {
    this.id = id;
    this.topics = topics;
    this.consumer = Utils.createConsumer(groupId, id);
  }

  @Override
  public void run() {
    try {
      consumer.subscribe(topics);
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
        for (ConsumerRecord<String, String> record : records) {
          Map<String, Object> map = new HashMap<>();
          map.put("partition", record.partition());
          map.put("offset", record.offset());
          map.put("value", record.value());
          System.out.println(this.id + ": " + map);
        }
      }
    } catch (WakeupException e) {
      // ignore for shutdown
    } finally {
      consumer.close();
    }
  }

  public void shutdown() {
    consumer.wakeup();
  }
  
  public static void main(String[] args) { 
    int numConsumers = 3;
    String groupId = "consumer-tutorial-group";
    List<String> topics = Arrays.asList("consumer-tutorial");
    ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

    final List<ConsumerLoop> consumers = new ArrayList<>();
    for (int i = 0; i < numConsumers; i++) {
      ConsumerLoop consumer = new ConsumerLoop(String.valueOf(i), groupId, topics);
      consumers.add(consumer);
      executor.submit(consumer);
    }

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        for (ConsumerLoop consumer : consumers) {
          consumer.shutdown();
        } 
        executor.shutdown();
        try {
          executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
