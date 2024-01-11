package com.barreto.stockmanagement.infra.config.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.barreto.stockmanagement.infra.config.rabbitMQ.RabbitMQData.*;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue(queueName, false, false, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchangeName, false, true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(directExchange())
                .with(routingKey);
    }
}
