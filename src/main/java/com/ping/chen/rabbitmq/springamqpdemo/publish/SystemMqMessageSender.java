package com.ping.chen.rabbitmq.springamqpdemo.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by ping.chen on 2018/5/6.
 */
@Component
public class SystemMqMessageSender {
    private static final Logger logger = LoggerFactory.getLogger(SystemMqMessageSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${publish.exchange.name.system}")
    private String exchangeSystem;

    @Value("${publish.queue.name.system}")
    private String queueSystem;

    @Resource
    private RabbitAdmin rabbitAdmin;

    public void sendMessage(String message) {
        logger.info("发送消息：{}", message);
        rabbitTemplate.convertAndSend(exchangeSystem, queueSystem, message);
    }

    //声明持久化队列，并绑定到exchange上
    @Bean
    public Binding bindingExchangeSystem() {
        Queue queue = QueueBuilder.durable(queueSystem).build();//队列持久化
        rabbitAdmin.declareQueue(queue);//声明队列
        DirectExchange exchange = (DirectExchange) ExchangeBuilder.directExchange(exchangeSystem).build();
        rabbitAdmin.declareExchange(exchange);//创建路由
        Binding binding = BindingBuilder.bind(queue).to(exchange).withQueueName();//绑定路由
        rabbitAdmin.declareBinding(binding);
        return binding;
    }
}
