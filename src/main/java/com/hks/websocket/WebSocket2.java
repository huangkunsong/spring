package com.hks.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class WebSocket2 extends AbstractWebSocketHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocket2.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LOGGER.info("service receive message:" + message.getPayload());
        Thread.sleep(2000);
        session.sendMessage(new TextMessage("服务端回复。"));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("222");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.info("Connection closed. Status:" + status);
    }
}
