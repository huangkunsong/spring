package com.hks.JMS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class JmsMiddle implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMiddle.class);

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                System.out.println(((TextMessage)message).getText());
            } else if (message instanceof MapMessage) {
                MapMessage mmsg = (MapMessage) message;
            }
        } catch (JMSException e) {
            LOGGER.error("", e);
        }
    }
}
