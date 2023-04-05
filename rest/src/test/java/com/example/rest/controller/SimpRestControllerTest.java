package com.example.rest.controller;

import static com.example.common.messaging.Queues.QUEUE_BACK_SERVICE;

import com.example.common.messaging.MessageSender;
import com.example.common.model.RandomIntObject;
import com.example.common.model.Transfer;
import com.example.common.service.RandomIntService;
import com.example.rest.RestApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApplication.class)
@AutoConfigureMockMvc
class SimpRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SimpRestController controller;

    @MockBean
    private MessageSender messageSender;

    @MockBean
    private RandomIntService service;

    @Test
    public void onlySimpControllerIsLoaded(){
        assertThat(controller).isNotNull();
    }

    @Test
    public void shouldReturnNoContent() throws Exception {
        when(service.save(any(RandomIntObject.class))).thenReturn(new RandomIntObject());
        this.mockMvc.perform(get("/random")).andDo(print()).andExpect(status().isNoContent());
        verify(service, times(1)).save(any(RandomIntObject.class));
        verify(messageSender, times(1)).sendMessage(eq(QUEUE_BACK_SERVICE), any(Transfer.class));
    }

}