package com.example.adminservice.service;

import com.example.adminservice.customexception.FlightNotFoundException;
import com.example.adminservice.dto.FlightEvent;
import com.example.adminservice.entity.Flight;
import com.example.adminservice.publisher.RabbitMqProducer;
import com.example.adminservice.repo.FlightRepo;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
  private final RabbitMqProducer mqProducer;
  private final FlightRepo flightRepo;

  public Flight addFlight(Flight flight) {
    Flight savedFlight = flightRepo.save(flight);

    mqProducer.sendFlightEvent(new FlightEvent("ADD", savedFlight));
    logger.info("Sending data to the Rabbitmq {}", savedFlight);
    return savedFlight;

  }

  public List<Flight> getAll() {
    return flightRepo.findAll();
  }

  public Flight updateFlight(Long id, Flight flight) {
    flightRepo.findById(id).orElseThrow(() -> new FlightNotFoundException("Flight not found with ID: " + id));

    flight.setId(id);
    Flight updated = flightRepo.save(flight);

    mqProducer.sendFlightEvent(new FlightEvent("UPDATE", updated));
    return updated;
  }

  public String delteFlight(Long id) {
    Flight flight = flightRepo.findById(id).orElseThrow(
        () -> new FlightNotFoundException("Flight not found with ID: " + id));
    flightRepo.deleteById(id);
    mqProducer.sendFlightEvent(new FlightEvent("DELETE", flight));
    return "FLight with id :" + id + " is deleted";
  }

  public Flight getById(Long id) {
    return flightRepo.findById(id).orElseThrow(() -> new FlightNotFoundException("Flight not found with ID: " + id));
  }
}
