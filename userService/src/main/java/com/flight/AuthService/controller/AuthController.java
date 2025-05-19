package com.flight.AuthService.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.flight.AuthService.dto.LoginRequest;
import com.flight.AuthService.dto.LoginResponse;
import com.flight.AuthService.dto.SignupRequest;
import com.flight.AuthService.models.Role;
import com.flight.AuthService.models.User;
import com.flight.AuthService.repo.UserRepository;
import com.flight.AuthService.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "AuthController", description = "Handles user signup and login")
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @PostMapping("/signup")
  @Operation(summary = "User signup", description = "Register a new user with username, email, password and roles")
  public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
    try {
      if (userRepository.findByUserName(request.getUserName()).isPresent()) {
        return ResponseEntity.badRequest().body("userName already taken !");
      }

      System.out.println(request);
      Set<Role> roles = request.getRoles();
      System.out.println(request.getRoles());
      if (roles == null || roles.isEmpty()) {
        roles = new HashSet<>();
        roles.add(Role.USER);
        request.setRoles(roles);

      } 

      User user = new User(null, request.getUserName(), request.getEmail(),
          passwordEncoder.encode(request.getPassword()), request.getRoles());

      userRepository.save(user);

      return ResponseEntity.ok("User registred succesfully");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500).body("user not created");
    }
  }

  @PostMapping("/login")
  @Operation(summary = "User login", description = "Authenticate user and return JWT token")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    // Optional<User> userOpt =
    // userRepository.findByUserName(request.getUsername());
    // if(userOpt.isEmpty() ||
    // !passwordEncoder.matches((request.getPassword()),userOpt.get().getPassword())){
    // return ResponseEntity.status(401).body("Invalid credentials");
    // }
    try {

      Authentication authenticate = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

      if (authenticate.isAuthenticated()) {
        Optional<User> userOpt = userRepository.findByUserName(request.getUserName());
        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtUtil.generateToken(request.getUserName(), user.getRoles());
        LoginResponse loginResponse = new LoginResponse("Login Succesfull", token);
        return ResponseEntity.ok().body(loginResponse); // change to 201 http code
      } else {

        return ResponseEntity.status(401).body(new LoginResponse("Login Falied", null));
      }
    } catch (Exception e) {
      return ResponseEntity.status(401).body(new LoginResponse("Invalid credentials", null));
    }
  }

  // @PostMapping("/login")
  // public ResponseEntity<String> login(@RequestBody LoginRequest request) {
  // Authentication authenticate = authenticationManager.authenticate(
  // new UsernamePasswordAuthenticationToken(request.getUserName(),
  // request.getPassword()));

  // if (authenticate.isAuthenticated()) {
  // Optional<User> userOpt =
  // userRepository.findByUserName(request.getUserName());
  // User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not
  // found"));

  // String token = jwtUtil.generateToken(request.getUserName(), user.getRoles());

  // HttpHeaders headers = new HttpHeaders();
  // headers.set("Authorization", token); // Make sure to prefix with "Bearer "

  // return ResponseEntity.ok()
  // .headers(headers)
  // .body("Successful");
  // } else {
  // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid
  // Credentials");
  // }
  // }

}
