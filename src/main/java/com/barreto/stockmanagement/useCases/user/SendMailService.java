package com.barreto.stockmanagement.useCases.user;

import com.barreto.stockmanagement.infra.DTOs.user.UserSendWelcomeMailBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.barreto.stockmanagement.infra.config.rabbitMQ.RabbitMQData.exchangeName;
import static com.barreto.stockmanagement.infra.config.rabbitMQ.RabbitMQData.routingKey;

@Log4j2
@Service
@RequiredArgsConstructor
public class SendMailService implements SendMailUseCase {
    private final RabbitTemplate rabbitTemplate;

    public void sendWelcomeMail(UserSendWelcomeMailBody user) {
      log.info("Sending a message to exchange: " + user);

      MessageProperties properties = MessagePropertiesBuilder.newInstance().setContentType("application/json").build();

      try {
          rabbitTemplate.convertAndSend(exchangeName, routingKey, new Message(new ObjectMapper().writeValueAsBytes(user), properties));
      } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
      }
    }
}
