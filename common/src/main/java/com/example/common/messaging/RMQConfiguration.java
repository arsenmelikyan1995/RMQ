package com.example.common.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.Arrays;
import java.util.List;

import static com.example.common.messaging.Queues.QUEUE_BACK_SERVICE;
import static com.example.common.messaging.Queues.QUEUE_REST_SERVICE;
import static com.example.common.messaging.Queues.TOPIC;

@Configuration
@EnableRabbit
public class RMQConfiguration implements RabbitListenerConfigurer {

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(TOPIC);
    }

    @Bean
    public Queue queueRest() {
        return new Queue(QUEUE_REST_SERVICE, false);
    }

    @Bean
    public Queue queueBackService() {
        return new Queue(QUEUE_BACK_SERVICE, false);
    }

    @Bean
    public List<Binding> binding() {
        return Arrays.asList(
                BindingBuilder.bind(queueRest()).to(exchange()),
                BindingBuilder.bind(queueBackService()).to(exchange()));
    }

    @Override
    public void configureRabbitListeners(
            RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }
}
