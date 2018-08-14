package com.ping.chen.rabbitmq.demo.receive;

import com.ping.chen.rabbitmq.demo.config.RabbitmqConfig;
import com.ping.chen.rabbitmq.springamqpdemo.constant.Constant;
import com.rabbitmq.client.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by ping.chen on 2018/6/16.
 */
@Component
public class FanoutReceiver1 {

    static ConnectionFactory connectionFactory = new RabbitmqConfig().connectionFactory();

    private final static String QUEUE_NAME = Constant.FANOUT_QUEUE1;

    public static void main(String[] args) {
        receive();
    }
    public static void receive() {
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            //channel.basicQos(1);//消费者每次只接收一条消息
            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(FanoutReceiver1.class + " [x] Received '" + message + "'");
                    Long deliveryTag = envelope.getDeliveryTag();
                    channel.basicAck(deliveryTag, true);
                }
            };
            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
