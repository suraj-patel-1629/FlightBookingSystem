package com.example.bookingservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.bookingservice.dto.BookingEvent;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.model.Flight;
import com.example.bookingservice.repo.BookingRepo;
import com.example.bookingservice.repo.FlightRepo;

@Service

public class BookingService {
  @Autowired
  private BookingRepo bookingRepo;
  @Autowired
  private FlightRepo flightRepo;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchange.name3}")
  private String paymentExchange;

  @Value("${rabbitmq.routing.key.name3}")
  private String paymentRoutingkey;

  public Booking bookFlight(Long flightId, String email, int seats) {
    Flight flight = flightRepo.findById(flightId).orElseThrow(() -> new RuntimeException("Flight not found"));

    if (flight.getAvailableSeats() < seats) {
      throw new RuntimeException("Not enough seats available");

    }
    // flight.setAvailableSeats(flight.getAvailableSeats() - seats);

    // flightRepo.save(flight);

    double amount = flight.getPrice() * seats;
    Booking booking = new Booking(null, flightId, email, seats, LocalDateTime.now(), amount, "PENDING",
        flight.getFlightNumber());

    Booking saveBooking = bookingRepo.save(booking);

    BookingEvent event = new BookingEvent();
    event.setBookingId(saveBooking.getFlightId());
    event.setUserEmail(saveBooking.getUserEmail());
    event.setSeatsBooked(saveBooking.getSeatsBooked());
    event.setFlightNumber(flight.getFlightNumber());
    event.setFlightId(saveBooking.getFlightId());
    event.setPrice(saveBooking.getPrice());
    event.setStatus(saveBooking.getStatus());

     rabbitTemplate.convertAndSend(paymentExchange, paymentRoutingkey, event);

    return saveBooking;

  }

  public List<Booking> getBooking(String email) {
    return bookingRepo.findByUserEmail(email);
  }

}
