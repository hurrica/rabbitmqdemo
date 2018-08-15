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
public class DirectorTest {
    private static final String EXCHANGE_NAME = Constant.DIRECT_EXCHANGE;
    private static final String ROUTING_KEY = Constant.DIRECT_ROUTING_KEY;

    static ConnectionFactory connectionFactory = new RabbitmqConfig().connectionFactory();

    public static void main(String[] args) {
        sendMessage("fanout exchange test ");
    }

    public static void sendMessage(String message) {
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            for (int i = 0; i < 10; i++) {
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,null, (message+i).getBytes("utf-8"));
            }
            System.out.println("send message:" + message);
            channel.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
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
