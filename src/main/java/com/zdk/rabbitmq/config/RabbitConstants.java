package com.zdk.rabbitmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description rabbitmq配置类
 * @author: zhangDongkun
 * @date: 2019-08-26 22:36
 **/
@Data
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Component
public class RabbitConstants {



    private String host;

    private Integer port;

    private String username;

    private String password;

    private Boolean publisherConfirms;

    private String virtualHost;
}
