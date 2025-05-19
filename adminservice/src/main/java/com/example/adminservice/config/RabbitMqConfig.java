package com.example.adminservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
// import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMqConfig {
  // public static final String EXCHANGE ="flight.exchange";
  // public static final String ROUTING_KEY= "flight.event";
  // public static final String OUEUE = "flight.queue";
  @Value("${rabbitmq.queue.name}")
  private String queue;

  @Value("${rabbitmq.queue.name2}")
  private String queue2;

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.routing.key.name}")
  private String routingKey;

  @Value("${rabbitmq.routing.key.name2}")
  private String routingKey2;

  @Bean
  public Queue queue() {
    return new Queue(queue,true);
  }

  @Bean
  public Queue bookingQueue() {
    return new Queue(queue2);
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  // @Bean
  // public FanoutExchange fanoutExchange() {
  // return new FanoutExchange(exchange);
  // }

  @Bean
  public Binding binding1() {
    return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
  }

  @Bean
  public Binding binding2() {
    return BindingBuilder.bind(bookingQueue()).to(exchange()).with(routingKey2);
  }

  // @Bean
  // public Binding bindBookingQueue(){
  // return BindingBuilder.bind(bookingQueue()).to(fanoutExchange());
  // }

  // @Bean
  // public Binding binding(){
  // return BindingBuilder.bind(queue()).to(fanoutExchange());
  // }

  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(messageConverter());
    return template;
  }

}
