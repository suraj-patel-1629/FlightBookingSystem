package com.flight.AuthService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
   @Schema(description = "Loggin result message",example = "Login succesfull")
  private String message;
  @Schema(description = "Token used for authentication", example = "eysjfhkdfjew.jeifsdlfsdfkjfhoif")
  private String token;
}
