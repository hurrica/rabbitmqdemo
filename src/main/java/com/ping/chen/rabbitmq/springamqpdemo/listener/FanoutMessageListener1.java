package com.ping.chen.rabbitmq.springamqpdemo.listener;

import com.ping.chen.rabbitmq.springamqpdemo.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Constant.FANOUT_QUEUE1)
public class FanoutMessageListener1 {
    private static final Logger logger = LoggerFactory.getLogger(SystemMessageListener.class);

    private String queueName = Constant.FANOUT_QUEUE1;

    @RabbitHandler
    public void process(String message) {//监听消息
        logger.info("FanoutMessageListener1接收到消息：{}", message);
        processMessage(message, queueName);
    }

    public void processMessage(String content, String queueName) {
        //业务处理
    }
}
