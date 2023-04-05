package com.example.backserver.messaging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.backserver.BackServerApplication;
import com.example.common.messaging.MessageSender;
import com.example.common.model.RandomIntObject;
import com.example.common.model.Transfer;
import com.example.common.service.RandomIntService;
import com.example.common.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.example.common.messaging.Queues.QUEUE_REST_SERVICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackServerApplication.class)
public class MessageHandlerTest {

    @Autowired
    private MessageHandler handler;

    @MockBean
    private MessageSender messageSender;

    @MockBean
    private RandomIntService service;

    @Test
    public void onlyMessageHandlerIsLoaded(){
        assertThat(handler).isNotNull();
    }

    @Test
    public void whenCorrectData_ThenLogMessage() {
        Logger logger =(Logger) LoggerFactory.getLogger(MessageHandler.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        List<Integer> randomList = Utils.generateRandomInt();
        Transfer transfer = new Transfer("testId");
        RandomIntObject rOb = new RandomIntObject(randomList);
        rOb.setId("testId");
        doReturn(rOb)
                .when(service)
                .get(transfer.getId());
        handler.handleMessage(transfer);
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Get RandomIntObject from id" +transfer.getId(),logsList.get(0).getMessage());
        assertEquals(Level.INFO, logsList.get(0)
                .getLevel());
        verify(service, times(1)).get(eq(transfer.getId()));
        verify(messageSender, times(1)).sendMessage(eq(QUEUE_REST_SERVICE),eq(rOb));
    }


    @Test(expected = NullPointerException.class)
    public void whenTransferObjectNull_ThenThrowException() {

        handler.handleMessage(null);
        verify(service, times(0)).get(eq(null));
        verify(messageSender, times(0)).sendMessage(eq(QUEUE_REST_SERVICE),any(RandomIntObject.class));
    }
}
