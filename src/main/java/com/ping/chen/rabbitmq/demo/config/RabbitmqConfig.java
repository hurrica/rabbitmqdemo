package com.ping.chen.rabbitmq.demo.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ping.chen on 2018/6/16.
 */

public class RabbitmqConfig {
    private static final ConnectionFactory connection;
    static {
        connection = new ConnectionFactory();
        connection.setHost("localhost");
        connection.setPort(5672);
        connection.setUsername("chen");
        connection.setPassword("chen");
    }
    public static ConnectionFactory getConnection(){
        return connection;
    }

}
