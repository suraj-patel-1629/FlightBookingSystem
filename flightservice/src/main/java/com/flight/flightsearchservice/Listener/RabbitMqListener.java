package com.flight.flightsearchservice.Listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flight.flightsearchservice.dto.FlightEvent;
import com.flight.flightsearchservice.model.Flight;
import com.flight.flightsearchservice.repo.FlightRepository;

@Service
public class RabbitMqListener {
  
  @Autowired
  private FlightRepository flightRepository;

  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void handleFlightEvent(FlightEvent event){
    Flight flight =event.getFlight();

    switch(event.getEventType()){
      case "ADD":
      case "UPDATE":
        flightRepository.save(flight);
        break;
       case "DELETE":
       flightRepository.deleteById(flight.getId()); 
       break;
    }

  }
}
