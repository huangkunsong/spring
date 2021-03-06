package com.hks.configuration;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.*;

/*@Configuration*/
public class ActiveMQConfig {

    @Bean
    public ConnectionFactory mqFactory() {
        ActiveMQConnectionFactory mq = new ActiveMQConnectionFactory();
        mq.setBrokerURL("tcp://localhost:61616");
        return mq;
    }

    @Bean
    @Primary
    public JmsTemplate jmsQueue(
        ConnectionFactory connectionFactory,
        ActiveMQQueue activeMQQueue,
        MessageConverter messageConverter) {
        return getJmsTemplate(connectionFactory, activeMQQueue, messageConverter);
    }

    @Bean
    public JmsTemplate jmsTopic(
        ConnectionFactory connectionFactory,
        ActiveMQTopic activeMQTopic,
        MessageConverter messageConverter) {
        return getJmsTemplate(connectionFactory, activeMQTopic, messageConverter);
    }

    @NotNull
    private JmsTemplate getJmsTemplate(
        ConnectionFactory connectionFactory,
        Destination mq,
        MessageConverter messageConverter) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultDestination(mq);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public DefaultMessageListenerContainer queueJmsListener(
        ConnectionFactory factory,
        ActiveMQQueue queue) {
        return listenerContainer(factory, queue);
    }

    @Bean
    public DefaultMessageListenerContainer topicJmsListener(
        ConnectionFactory factory,
        ActiveMQTopic topic) {
        return listenerContainer(factory, topic);
    }

    @NotNull
    private DefaultMessageListenerContainer listenerContainer(
        ConnectionFactory factory,
        Destination destination) {
        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        MessageListenerAdapter listener = new MessageListenerAdapter();
        listener.setDelegate((MessageListener)(Message message) -> {
            try {
                if (message instanceof TextMessage) {
                    System.out.println(((TextMessage)message).getText());
                } else if (message instanceof MapMessage) {
                    MapMessage mmsg = (MapMessage) message;
                }
            } catch (JMSException e) {
            }
        });
        dmlc.setMessageListener(listener);
        dmlc.setConnectionFactory(factory);
        dmlc.setDestination(destination);
        return dmlc;
    }

    /**
     * 设置JMS发送/接收消息转换器
     * @return
     */
    @Bean
    public MessageConverter mappingJackson2MessageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setEncoding("UTF-8");
        converter.setTargetType(MessageType.TEXT);
        return converter;
    }

    /**
     * 发送接收JMS消息队列
     * @return
     */
    @Bean
    public ActiveMQQueue activeMQQueue() {
        return new ActiveMQQueue("database.queue");
    }

    /**
     * 发送接收JMS消息主题
     * @return
     */
    @Bean
    public ActiveMQTopic activeMQTopic() {
        return new ActiveMQTopic("database.topic");
    }
}
