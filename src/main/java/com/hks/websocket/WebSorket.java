package com.hks.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class WebSorket extends AbstractWebSocketHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(WebSorket.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LOGGER.info("service receive message:" + message.getPayload());
    }
}
