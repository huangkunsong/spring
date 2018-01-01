package com.hks.configuration;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {

    @Bean
    public ConnectionFactory mqFactory() {
        ActiveMQConnectionFactory mq = new ActiveMQConnectionFactory();
        mq.setBrokerURL("tcp:localhost:61616");
        return mq;
    }

    @Bean
    public ActiveMQQueue activeMQQueue() {
        return new ActiveMQQueue("database.queue");
    }

    @Bean
    public ActiveMQTopic activeMQTopic() {
        return new ActiveMQTopic("database.topic");
    }
}
