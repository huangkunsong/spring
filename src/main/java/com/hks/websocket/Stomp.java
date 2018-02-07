package com.hks.websocket;

import com.hks.entity.StompMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class Stomp {
    private static final Logger LOGGER = LoggerFactory.getLogger(Stomp.class);

    /**
     * 可以在任意地方注入,推送消息
     */
    @Autowired
    private SimpMessageSendingOperations messaging;
    /**
     * 不添加@SendTo返回的消息将发布到/topic/user
     * @SendTo重载消息目的地
     * @param stomp
     * @return
     */
    @SendTo("/topic/topicUser")
    @MessageMapping("/user")
    public StompMessage handleStomp(StompMessage stomp){
        LOGGER.info(stomp.getMessage());
        stomp.setMessage("服务端回复:" + stomp.getMessage());
        return stomp;
    }

    @MessageMapping("/hi")
    public void handleHi(Principal principal, StompMessage stomp) {
        messaging.convertAndSend("/topic/hi", "hi,Java");
    }

    /**
     * MessageExceptionHandler，WebSocket报错时触发
     * 记录和推送错误信息
     * @param e
     * @return
     */
    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable e){
        LOGGER.error(e.getMessage(), e);
        return e.getMessage();
    }
}
