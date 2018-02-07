package com.hks.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * 启动WebSocket且配置了基于代理的STOMP
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry regist) {
        /**
         * 将/socket路径注册为STOMP端点,启动SockJs
         * 客户端在订阅或发布消息到目的地前,要链接该端点
         */
        regist.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 不重载将生成一个简单的内存消息代理,用来处理/app前缀的消息。
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * enableSimpleBroker生成一个基于内存的消息代理,将处理/queue,topic前缀的消息
         * 应用程序处理@MessageMapping注解的控制器方法返回值也会路由到此代理中,
         * 并最终发送给订阅这些目的地的客户端。
         */
        registry.enableSimpleBroker("/queue", "/topic");
        /**
         * 发往应用程序的消息带有/app前缀,会路由到带有@MessageMapping注解的控制器方法中。
         */
        registry.setApplicationDestinationPrefixes("/app");
    }
}
