package com.flight.AuthService.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flight.AuthService.models.User;



@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   Optional<User> findByUserName(String username);
  
}
