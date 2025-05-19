package com.flight.flightsearchservice.dto;

import com.flight.flightsearchservice.model.Flight;

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
