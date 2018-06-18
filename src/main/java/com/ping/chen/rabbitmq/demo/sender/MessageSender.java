package com.ping.chen.rabbitmq.demo.sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * Created by ping.chen on 2018/6/16.
 */
@Component
public class MessageSender {
    public static final String QUEUE_NAME = "hello_world";

    @Autowired
    ConnectionFactory connectionFactory;

    public void sendMessage(String message) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish(" ", QUEUE_NAME,null, message.getBytes("utf-8"));

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
