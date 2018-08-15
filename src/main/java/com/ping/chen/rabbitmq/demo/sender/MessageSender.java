package com.ping.chen.rabbitmq.demo.sender;

import com.ping.chen.rabbitmq.demo.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * Created by ping.chen on 2018/6/16.
 */
@Component
public class MessageSender {
    public static final String ROUTING_KEY = "test_queue";
    private static final String EXCHANGE_NAME = "test_exchange_direct";

    static ConnectionFactory connectionFactory = RabbitmqConfig.getConnection();

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        String message;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            String routingKey = "error";
            message = routingKey + " level message";
            channel.basicPublish(EXCHANGE_NAME, routingKey,null, message.getBytes("utf-8"));

            System.out.println("send message:" + message);
        } finally {
            if (Objects.nonNull(channel)){
                channel.close();
            }
            if (Objects.nonNull(connection)){
                connection.close();
            }
        }
    }
}
