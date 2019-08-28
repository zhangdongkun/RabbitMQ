package com.zdk.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;

/**
 * @description
 * @author: zhangDongkun
 * @date: 2019-08-26 22:36
 **/
@Configuration
public class RabbitConfig {

    public final static String ROUTING_KEY = "test-key";
    public static final String EXCHANGE_DIREC = "exchange_direc";
    public static final String EXCHANGE_topic = "exchange_topic";
    public static final String QUEUE_DIREC = "queue_direc";
    public static final String QUEUE_TOPIC = "queue_dtopic";


    @Resource
    private RabbitConstants rabbitConstants;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitConstants.getHost());
        connectionFactory.setUsername(rabbitConstants.getUsername());
        connectionFactory.setVirtualHost(rabbitConstants.getVirtualHost());
        connectionFactory.setPassword(rabbitConstants.getPassword());
        //        * 如果要进行消息回调，则这里必须要设置为true
        connectionFactory.setPublisherConfirms(rabbitConstants.getPublisherConfirms());
        return connectionFactory;
    }

    /**
     * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }


    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

    // 创建队列
    @Bean(name = QUEUE_DIREC)
    public Queue queueDirec() {
        return new Queue(QUEUE_DIREC);
    }

    // 创建队列
    @Bean(name = "topic")
    public Queue queueTopic() {
        return new Queue(QUEUE_TOPIC);
    }


    // 创建一个 topic 类型的交换器
    @Bean
    public TopicExchange exchangeTopic() {
        return new TopicExchange(EXCHANGE_topic);
    }


    // 创建一个 direct 类型的交换器
    @Bean
    @Primary
    public DirectExchange exchangeDirec() {
        return new DirectExchange(EXCHANGE_DIREC);
    }

    // 使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）
    @Bean
    public Binding bindingDirec(@Qualifier(QUEUE_DIREC) Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding bindingTopic(@Qualifier("topic") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("topic");
    }


}
