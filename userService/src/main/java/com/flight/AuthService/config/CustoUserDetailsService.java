package com.flight.AuthService.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import com.flight.AuthService.models.User;
import com.flight.AuthService.repo.UserRepository;
@Component
public class CustoUserDetailsService implements  UserDetailsService{
  
  @Autowired
  private UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

   Optional<User> credentials = userRepository.findByUserName(username);
  
   return credentials.map(CusttomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("user not found with name "+username));
  }

}
