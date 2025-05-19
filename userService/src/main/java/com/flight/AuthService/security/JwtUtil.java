package com.flight.AuthService.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.flight.AuthService.models.Role;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  
  private final SecretKey secretKey=Keys.hmacShaKeyFor("MySuperSecretKeyForJWTMySuperSecretKeyForJWT".getBytes());

  public String generateToken(String username,Set<Role>roles){
     
    String authorities = roles.stream().map(Role::name).collect(Collectors.joining(","));

    return Jwts.builder().setSubject(username).
    claim("roles",authorities).
    setIssuedAt(new Date()).
    setExpiration(new Date(System.currentTimeMillis()+ 86400000)).signWith(secretKey,SignatureAlgorithm.HS256).compact();
  }

  public String extractUsername(String token){
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
  }
  
  public boolean validateToken(String token){
    try{
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
      return true;
    }catch(JwtException e){
      return false;
    }
  }
}
