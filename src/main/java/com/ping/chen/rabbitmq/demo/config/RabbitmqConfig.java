package com.ping.chen.rabbitmq.demo.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ping.chen on 2018/6/16.
 */
@Configuration
public class RabbitmqConfig {
    @Bean
    public ConnectionFactory connectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("chen");
        factory.setPassword("chen");
        return factory;
    }

}
