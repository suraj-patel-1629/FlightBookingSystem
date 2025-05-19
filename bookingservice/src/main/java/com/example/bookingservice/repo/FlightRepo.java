package com.example.bookingservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingservice.model.Flight;

@Repository
public interface FlightRepo extends JpaRepository<Flight,Long>{

}
