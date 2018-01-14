package com.hks.controller;

import com.hks.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/rabbit", produces = MediaType.APPLICATION_JSON_VALUE)
public class RabbitMQController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        User user = new User();
        user.setUserName("我的时间");
        user.setPassWord("asdasd");
        user.setDate(new Date());
        rabbitTemplate.convertAndSend(user);
    }
}
