package com.ping.chen.rabbitmq.springamqpdemo.publish;

import com.ping.chen.rabbitmq.springamqpdemo.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ping.chen on 2018/5/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource("classpath:rabbitmq.properties")
public class SystemMqMessageSenderTest {
    @Autowired
    SystemMqMessageSender systemMqMessageSender;
    @Autowired
    FanoutExchangeTest fanoutExchangeTest;

    @Test
    public void sendMessage() throws Exception {
        int i=0;
        while (i < 5){
            i++;
            fanoutExchangeTest.sendMessage("hello world " + i);
        }
    }


}
