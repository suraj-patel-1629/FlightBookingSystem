package com.flight.AuthService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {
  
 
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
       .csrf(csrf-> csrf.disable())
       .authorizeHttpRequests(auth->auth.requestMatchers("/user/signup","/user/login").permitAll()
       .anyRequest().authenticated()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService(){
    return new CustoUserDetailsService();
    }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
    return config.getAuthenticationManager();

  }
   @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;


}}
