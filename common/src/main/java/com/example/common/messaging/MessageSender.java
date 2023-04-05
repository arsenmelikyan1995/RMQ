package com.example.common.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String queueName, Object event) {
        LOG.debug("Sending  message to Queue '" + queueName);
        rabbitTemplate.convertAndSend(queueName, event);
    }
}
