package com.hks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hks.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jms")
public class JmsController {

    @Autowired
    JmsTemplate jmsQueue;

    @Autowired
    @Qualifier("jmsTopic")
    JmsTemplate jmsTopic;

    @RequestMapping(value = "/sendJms", method = RequestMethod.GET)
    public void testJms() throws JsonProcessingException {
        final User user = new User();
        user.setId(1123);
        user.setLocked(false);
        user.setPassWord("123123");
        user.setUserName("张三");
        jmsQueue.convertAndSend(user);
        jmsTopic.convertAndSend(user);
    }
}
