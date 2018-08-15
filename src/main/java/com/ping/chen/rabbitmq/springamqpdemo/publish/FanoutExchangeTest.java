package com.ping.chen.rabbitmq.springamqpdemo.publish;

import com.ping.chen.rabbitmq.springamqpdemo.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by ping.chen on 2018/5/6.
 */
@Component
public class FanoutExchangeTest {
    private static final Logger logger = LoggerFactory.getLogger(FanoutExchangeTest.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private String exchangeSystem = Constant.FANOUT_EXCHANGE;

    private String queueSystem = Constant.FANOUT_QUEUE1;

    @Resource
    private RabbitAdmin rabbitAdmin;

    public void sendMessage(String message) {
        logger.info("消息发送到{}：{}", exchangeSystem, message);
        rabbitTemplate.convertAndSend(exchangeSystem, queueSystem, message);
    }

    //声明持久化队列，并绑定到exchange上
    @Bean
    public Binding bindingFanoutExchange1() {
        Queue queue = QueueBuilder.durable(queueSystem).build();//队列持久化
        rabbitAdmin.declareQueue(queue);//声明队列
        FanoutExchange exchange = (FanoutExchange) ExchangeBuilder.fanoutExchange(exchangeSystem).build();
        rabbitAdmin.declareExchange(exchange);//创建路由
        Binding binding = BindingBuilder.bind(queue).to(exchange);//绑定路由
        rabbitAdmin.declareBinding(binding);
        return binding;
    }
}
