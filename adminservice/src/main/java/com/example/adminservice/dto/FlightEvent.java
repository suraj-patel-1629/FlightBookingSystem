package com.example.adminservice.dto;

import com.example.adminservice.entity.Flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightEvent {
  
  private String eventType;
  private Flight flight;
}
