package com.example.bookingservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
  
  @Id
  private Long id;
   private String flightNumber;
   private String airline;
   private String fromLocation;
   private String toLocation;
   private LocalDateTime arrivalTime;
   private LocalDateTime departureTime;
   private int availableSeats;
   private double price;
}
