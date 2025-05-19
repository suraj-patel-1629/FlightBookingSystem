package com.example.ApiGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
@Autowired
private CustomJwtGrantedAuthoritiesConverter customJwtGrantedAuthoritiesConverter;

  private static final String SECRET_KEY = "MySuperSecretKeyForJWTMySuperSecretKeyForJWT"; // Same as Auth Service

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(exchanges -> exchanges.pathMatchers(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/auth/v3/api-docs",
            "/admin/v3/api-docs",
            "/flight/v3/api-docs",
            "/booking/v3/api-docs",
            "/payment/v3/api-docs",
            "/checkin/v3/api-docs").permitAll()
            .pathMatchers("/user/**").permitAll()
            .pathMatchers("/admin/**").hasRole("ADMIN")
            .anyExchange().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder()).jwtAuthenticationConverter(jwtAuthenticationConvertor()))) // Reactive JWT validation
        .build();
  }

  @Bean
  public ReactiveJwtDecoder jwtDecoder() {
    byte[] keyBytes = SECRET_KEY.getBytes(); // Convert String key to byte array
    SecretKey secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"); // Create HMAC key
    return NimbusReactiveJwtDecoder.withSecretKey(secretKey)
    .macAlgorithm(MacAlgorithm.HS256)
    .build(); // Reactive JWT decoder
  }
  @Bean
  public ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConvertor(){

     JwtAuthenticationConverter jwtAuthenticationConvertor= new JwtAuthenticationConverter();
     jwtAuthenticationConvertor.setJwtGrantedAuthoritiesConverter(customJwtGrantedAuthoritiesConverter);
     return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConvertor);
  }
}
