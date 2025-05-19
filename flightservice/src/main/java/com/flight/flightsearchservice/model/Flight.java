package com.flight.flightsearchservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
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
