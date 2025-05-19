package com.example.ApiGateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.server.WebFilter;

@Configuration
public class SecurityLoggingFilter {
  private static final Logger logger = LoggerFactory.getLogger(SecurityLoggingFilter.class);

  @Bean
  public WebFilter loggingFilter() {
    return (exchange, chain) -> {
      logger.info("Incoming request: " + exchange.getRequest().getURI());
      return chain.filter(exchange);
    };
  }
}
