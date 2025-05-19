package com.flight.AuthService.models;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userName;

  private String email;

  private String password;
   
   @ElementCollection(fetch=FetchType.EAGER)
   @CollectionTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"))
   @Column(name = "role")
  @Enumerated(EnumType.STRING)
   private Set<Role>roles;
}
