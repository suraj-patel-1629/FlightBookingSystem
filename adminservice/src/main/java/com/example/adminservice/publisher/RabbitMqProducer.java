package com.example.adminservice.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.adminservice.dto.FlightEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMqProducer {

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.routing.key.name}")
  private String routingKey;

  @Value("${rabbitmq.routing.key.name2}")
  private String routingKey2;

  private final RabbitTemplate rabbitTemplate;

  public void sendFlightEvent(FlightEvent event) {
    rabbitTemplate.convertAndSend(exchange, routingKey, event);
    rabbitTemplate.convertAndSend(exchange, routingKey2, event);
  }

}
