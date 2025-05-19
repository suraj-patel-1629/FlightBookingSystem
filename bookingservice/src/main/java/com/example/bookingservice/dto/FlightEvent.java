package com.example.bookingservice.dto;

import com.example.bookingservice.model.Flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightEvent {
  
  private String eventType;
  private Flight flight;
}
