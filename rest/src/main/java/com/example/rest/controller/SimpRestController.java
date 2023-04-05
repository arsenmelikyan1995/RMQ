package com.example.rest.controller;

import com.example.common.messaging.MessageSender;
import com.example.common.model.RandomIntObject;
import com.example.common.model.Transfer;
import com.example.common.service.RandomIntService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.common.messaging.Queues.QUEUE_BACK_SERVICE;
import static com.example.common.utils.Utils.generateRandomInt;

@RestController
public class SimpRestController {

    private static final Logger LOG = LoggerFactory.getLogger(SimpRestController.class);

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private RandomIntService service;

    @GetMapping("/random")
    public ResponseEntity<Void> random() {
        LOG.info("Generating random Integers");
        RandomIntObject obj = new RandomIntObject(generateRandomInt());
        service.save(obj);
        messageSender.sendMessage(QUEUE_BACK_SERVICE, new Transfer(obj.getId()));
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
