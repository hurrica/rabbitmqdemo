package com.ping.chen.rabbitmq.springamqpdemo.listener;

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
public class SystemMessageListener1 {

    private static final Logger logger = LoggerFactory.getLogger(SystemMessageListener1.class);

    @Value("${listen.queue.name.system}}")
    private String queueName;

    @RabbitHandler
    public void process(String message) {//监听消息
        logger.info("SystemMessageListener1接收到消息：{}", message);
        processMessage(message, queueName);
    }

    public void processMessage(String content, String queueName) {

        //业务处理
    }
}
