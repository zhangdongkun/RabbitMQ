package com.zdk.rabbitmq;

import com.zdk.rabbitmq.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description xiaof
 * @author: zhangDongkun
 * @date: 2019-08-28 22:29
 **/
@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_DIREC)
    public void consumeMessage(String msg) {
       log.info("Message:{}",msg);
    }
}
