package com.flight.flightsearchservice.repo;

import com.flight.flightsearchservice.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByFromLocationAndToLocation(String from, String to);
    
}

