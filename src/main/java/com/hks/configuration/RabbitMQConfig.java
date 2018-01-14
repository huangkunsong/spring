package com.hks.configuration;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AMAP:
 * Produces  ->  exchange  -> bindings  -> queue  -> consumer
 * 嵌套exchange
 * Produces  ->  exchange  -> exchange  -> bindings  -> queue  -> consumer
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "exchange";
    public static final String ROUTINGKEY = "routingKey";

    /**
     * 实例化链接工厂
     *
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("127.0.0.1");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(AMQP.PROTOCOL.PORT);
        //设置分组
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    /**
     * 实例化模板,用于发送和同步接收AMQP
     * 必须是prototype类型
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    /*@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)*/
    public RabbitTemplate rabbitTemplate(
        ConnectionFactory connectionFactory,
        MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        //设置默认属性
        template.setEncoding("UTF-8");
        template.setExchange(EXCHANGE_NAME);
        template.setRoutingKey(ROUTINGKEY);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 实例化一个exchange
     * FanoutExchange:
     * 将消息分发到所有的绑定队列，无routingkey的概念
     * DirectExchange:
     * produces指定的routingkey要和
     * bingding的bingdingkey一致才会发送到绑定的队列
     * TopicExchange:
     * produces指定的routingkey要和
     * bingding的bingdingkey一致才会发送到绑定的队列
     * bingding支持通配符模式
     * #：表示0个或多个单词
     * *：表示一个单词
     * routingkey :  123.123.21.1
     * bindingkey :  123.#匹配
     * 123#不匹配  要有一个.
     * 123.123.*  不匹配,*只表示一个单词
     * HeadersExchange ：通过添加属性key-value匹配
     * Headers类型的exchange使用的比较少,它也是忽略routingKey的一种路由方式。
     * 是使用Headers来匹配的.Headers是一个键值对,可以定义成Hashtable.
     * 发送者在发送的时候定义一些键值对,接收者也可以再绑定时候传入一些键值对,
     * 两者匹配的话,则对应的队列就可以收到消息。
     * 匹配有两种方式all和any.
     * 这两种方式是在接收端必须要用键值"x-mactch"来定义.
     * all代表定义的多个键值对都要满足0,而any则代码只要满足一个就可以了.
     * fanout,direct,topic exchange的routingKey都需要要字符串形式的,
     * 而headers exchange则没有这个要求,因为键值对的值可以是任何类型。
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    /**
     * 实例化一个队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("queue", true);
    }

    /**
     * 将queue和exchange进行绑定
     *
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(ROUTINGKEY);
    }

    /**
     * 异步AMQP消息监听器
     *
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            byte[] body = message.getBody();
            System.out.println("receive msg : " + new String(body, "UTF-8"));
            //确认消息成功消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        });
        return container;
    }
}
