package com.ping.chen.rabbitmq.demo.sender;

import com.ping.chen.rabbitmq.demo.config.RabbitmqConfig;
import com.ping.chen.rabbitmq.springamqpdemo.constant.Constant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * Created by ping.chen on 2018/6/16.
 */
@Component
public class FanoutTest1 {
    private static final String QUEUE_NAME = Constant.FANOUT_QUEUE1;
    private static final String EXCHANGE_NAME = Constant.FANOUT_EXCHANGE;
    private static final String ROUTING_KEY = Constant.FANOUT_ROUTING_KEY;

    static ConnectionFactory connectionFactory = new RabbitmqConfig().connectionFactory();

    public static void main(String[] args) {
        sendMessage("fanout exchange test ");
    }
    public static void sendMessage(String message) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            //声明队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            for (int i = 0; i < 10; i++) {
                channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME,null, (message+i).getBytes("utf-8"));
            }

            System.out.println("send message:" + message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(channel)){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(connection)){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
