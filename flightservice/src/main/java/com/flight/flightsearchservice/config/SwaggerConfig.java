package com.flight.flightsearchservice.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI myCustomConfig() {
    return new OpenAPI()
        .info(new Info()
            .title("Flight Service APIs")
            .description("By Suraj")
            .version("1.0"))
        .servers(List.of(
            new Server().url("http://localhost:8084").description("Local"),
            new Server().url("http://localhost:8083").description("Live")
            
        ));
        // .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        // .components(new Components()
        //     .addSecuritySchemes("bearerAuth",
        //         new SecurityScheme()
        //             .type(SecurityScheme.Type.HTTP)
        //             .scheme("bearer")
        //             .bearerFormat("JWT")
        //             .in(SecurityScheme.In.HEADER)
        //             .name("Authorization")
        //     )
        // );
  }
}
