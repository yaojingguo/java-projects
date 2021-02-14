package rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {

  public static void main(String[] args) throws InterruptedException, MQClientException {
    // Instantiate with specified consumer group name.
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("c_g_1");

    // Specify name server addresses.
    consumer.setNamesrvAddr(Conf.NAMESRV_ADDR);

    // Subscribe one more more topics to consume.
    consumer.subscribe(Conf.TOPIC_NAME, "*");

    // Register callback to execute on arrival of messages fetched from brokers.
    consumer.registerMessageListener(
        new MessageListenerConcurrently() {
          @Override
          public ConsumeConcurrentlyStatus consumeMessage(
              List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            //        System.out.printf("%s Receive New Messages: %s %n",
            // Thread.currentThread().getName(), msgs);
            System.out.printf("received %d messages\n", msgs.size());
            for (MessageExt msg : msgs) {
              System.out.printf("message body: %s\n", new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
          }
        });

    //Launch the consumer instance.
    consumer.start();

    System.out.printf("Consumer Started.%n");
  }
}
