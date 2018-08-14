package com.ping.chen.rabbitmq.demo.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ping.chen on 2018/6/16.
 */
public class RabbitmqConfig {

    public ConnectionFactory connectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.4.238");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("bluepay");
        factory.setVirtualHost("/");
        return factory;
    }

}
