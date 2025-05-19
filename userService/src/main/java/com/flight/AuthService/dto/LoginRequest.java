package com.flight.AuthService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  
  @Schema(description = "UserName of the user",example = "suraj_patel")
  @NotBlank(message = "User name should not be empty")
  private String userName;
  @Schema(description = "password of the user", example = "password")
  @NotBlank(message = "password is required")
  private String password;
}
