package com.hks.configuration;

import com.hks.websocket.WebSocket;
import com.hks.websocket.WebSocket2;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*@Configuration
@EnableWebSocket*/
public class WebSocketConfig implements WebSocketConfigurer {
    /**
     * 将/webSocket websocket请求转发到websocket处理
     * @param register
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry register) {
        register.addHandler(webSocket(), "/websocket").withSockJS();
        register.addHandler(webSocket2(), "/a").withSockJS();
    }




    @Bean
    public WebSocket webSocket() {
        return new WebSocket();
    }

    @Bean
    public WebSocket2 webSocket2() {
        return new WebSocket2();
    }
}
