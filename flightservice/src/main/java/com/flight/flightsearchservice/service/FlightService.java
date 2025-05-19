package com.flight.flightsearchservice.service;

import com.flight.flightsearchservice.model.Flight;
import com.flight.flightsearchservice.repo.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    @Autowired
    private final FlightRepository flightRepository;

    public List<Flight> searchFlights(String from, String to) {
         return flightRepository.findByFromLocationAndToLocation(from, to);

    }

}
