package com.example.rest.messaging;

import com.example.common.model.RandomIntObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.example.common.messaging.Queues.QUEUE_REST_SERVICE;

@Component
public class MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

    @RabbitListener(queues = QUEUE_REST_SERVICE)
    public void handleMessage(@Payload RandomIntObject obj) {
        for (Integer in : obj.getRandomInt())
            LOG.info("Random integer :" + in);

    }
}
