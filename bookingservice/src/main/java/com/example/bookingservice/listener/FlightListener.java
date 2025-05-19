package com.example.bookingservice.listener;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookingservice.dto.FlightEvent;
import com.example.bookingservice.model.Flight;
import com.example.bookingservice.repo.FlightRepo;

@Service
public class FlightListener {


  @Autowired
  private FlightRepo flightRepo;

  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void handelFlightEvent(FlightEvent event){
     
    Flight flight = event.getFlight();

    switch (event.getEventType()) {
      case "ADD":
      case "UPDATE":
        flightRepo.save(flight);
        break;
      
      case "DELETE":
        flightRepo.deleteById(flight.getId());
        break;
    }


  }

}




