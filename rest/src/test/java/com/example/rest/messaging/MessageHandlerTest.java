package com.example.rest.messaging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.common.model.RandomIntObject;
import com.example.common.utils.Utils;
import com.example.rest.RestApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApplication.class)
public class MessageHandlerTest {

    @Autowired
    private MessageHandler handler;

    @Test
    public void onlyMessageHandlerIsLoaded() {
        assertThat(handler).isNotNull();
    }

    @Test
    public void whenCorrectData_ThenLogMessage() {
        Logger logger = (Logger) LoggerFactory.getLogger(MessageHandler.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        List<Integer> randomList = Utils.generateRandomInt();
        RandomIntObject obj = new RandomIntObject(randomList);
        handler.handleMessage(obj);

        List<ILoggingEvent> logsList = listAppender.list;
        for (int i = 0; i < randomList.size(); i++) {
            assertEquals("Random integer :" + randomList.get(i), logsList.get(i).getMessage());
            assertEquals(Level.INFO, logsList.get(i).getLevel());
        }
    }

    @Test(expected = NullPointerException.class)
    public void whenNullRandomData_ThenThrowException() {
        RandomIntObject obj = new RandomIntObject();
        handler.handleMessage(obj);

    }
}