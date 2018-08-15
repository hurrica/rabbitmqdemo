package com.ping.chen.rabbitmq.demo.sender;

import com.ping.chen.rabbitmq.demo.config.RabbitmqConfig;
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
public class MessageSender2 {
    public static final String QUEUE_NAME = "hello_world2";

    static ConnectionFactory connectionFactory = new RabbitmqConfig().connectionFactory();

    public static void main(String[] args) {
        sendMessage("hello world");
    }
    public static void sendMessage(String message) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            for (int i = 0; i < 10; i++) {
                channel.basicPublish("", QUEUE_NAME,null, (message+i).getBytes("utf-8"));
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
