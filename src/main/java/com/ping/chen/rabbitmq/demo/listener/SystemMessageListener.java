package com.ping.chen.rabbitmq.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ping.chen on 2018/5/6.
 */
@Component
@RabbitListener(queues = "${listen.queue.name.system}")
public class SystemMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(SystemMessageListener.class);

    @Value("${listen.queue.name.system}}")
    private String queueName;

    @RabbitHandler
    public void process(String message) {//监听消息
        logger.info("接收到消息：{}", message);
        processMessage(message, queueName);
    }

    public void processMessage(String content, String queueName) {
        System.out.println(content);
        //业务处理
    }
}
