package com.ping.chen.rabbitmq.demo.publish;

import com.ping.chen.rabbitmq.demo.Application;
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

    @Test
    public void sendMessage() throws Exception {
        int i=0;
        while (i < 5){
            i++;
            systemMqMessageSender.sendMessage("hello world");
        }
    }


}
