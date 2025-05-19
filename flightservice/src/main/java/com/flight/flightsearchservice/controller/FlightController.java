package com.flight.flightsearchservice.controller;

import com.flight.flightsearchservice.model.Flight;
import com.flight.flightsearchservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping("/search")
    public ResponseEntity<?> searchFlights(@RequestParam String from, @RequestParam String to) {
        List<Flight> flights = flightService.searchFlights(from, to);
        List<Flight> filteredFlight = flights.stream()
                .filter(flight -> flight.getDepartureTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
        if (filteredFlight.isEmpty()) {
            return ResponseEntity.ok("There is no flight available right now.");
        }
        return ResponseEntity.ok(filteredFlight);
    }
 

    // need to add date base searching of flight
    
}
