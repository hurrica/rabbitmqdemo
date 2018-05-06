package com.ping.chen.rabbitmq.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by ping.chen on 2018/5/6.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("spring.config.location", "classpath:rabbitmq.properties");
        SpringApplication.run(Application.class, args);
    }
}
