package com.zdk.rabbitmq;

import com.zdk.rabbitmq.config.RabbitConfig;
import com.zdk.rabbitmq.config.RabbitConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {
    @Autowired
    RabbitConstants rabbitConstants;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void contextLoads() {
        System.out.println(rabbitConstants.toString());
    }

   @Test
   // ---------------------------------------------------- Direct 形式 -------------------------------------------- //

   public void publisher(){
        //	void send(String exchange, String routingKey, Message message) throws AmqpException;

        for(int i = 0;i < 100; i++) {

            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_DIREC,RabbitConfig.ROUTING_KEY,LocalDateTime.now().toString()+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        }


    }


}
