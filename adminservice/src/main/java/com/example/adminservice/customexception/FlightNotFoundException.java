package com.example.adminservice.customexception;

public class FlightNotFoundException extends RuntimeException {

  public FlightNotFoundException(String message) {
    super(message);
  }
}
