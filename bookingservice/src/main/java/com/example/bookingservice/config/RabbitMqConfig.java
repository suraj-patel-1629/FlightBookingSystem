package com.example.bookingservice.config;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
  
  @Value("${rabbitmq.queue.name2}")
  private String checkInqueue;

  @Value("${rabbitmq.exchange.name2}")
  private String checkInexchange;

  @Value("${rabbitmq.routing.key.name2}")
  private String checkInroutingkey;

  @Value("${rabbitmq.queue.name3}")
  private String paymentQueue;

  @Value("${rabbitmq.exchange.name3}")
  private String paymentExchange;

  @Value("${rabbitmq.routing.key.name3}")
  private String paymentRoutingkey;

 @Bean 
public Queue queue(){
  return new Queue(checkInqueue);
} 

@Bean
public TopicExchange exchange(){
  return new TopicExchange(checkInexchange);

}

@Bean
public Binding bind(){
  return BindingBuilder.bind(queue()).to(exchange()).with(checkInroutingkey);
}

@Bean
public Queue paymentQueue() {
  return new Queue(paymentQueue);
}

@Bean
public TopicExchange paymentExchange() {
  return new TopicExchange(paymentExchange);

}

@Bean
public Binding paymentbind() {
  return BindingBuilder.bind(paymentQueue()).to(paymentExchange()).with(paymentRoutingkey);
}



  @Bean
  public Jackson2JsonMessageConverter messageConverter(){
    return new Jackson2JsonMessageConverter();
  }
   
  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }

}
