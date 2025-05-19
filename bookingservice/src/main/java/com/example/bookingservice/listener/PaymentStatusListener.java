package com.example.bookingservice.listener;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.bookingservice.dto.BookingEvent;
import com.example.bookingservice.dto.PaymentStatusEvent;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.model.Flight;
import com.example.bookingservice.repo.BookingRepo;
import com.example.bookingservice.repo.FlightRepo;

@Service
public class PaymentStatusListener {

   @Autowired
   private BookingRepo bookingRepo;

   @Autowired
   private FlightRepo flightRepo;

   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Value("${rabbitmq.exchange.name2}")
   private String checkInExchange;

   @Value("${rabbitmq.routing.key.name2}")
   private String checkInRoutingKey;

   @RabbitListener(queues = { "${rabbitmq.queue.name4}" })
   public void paymentStatus(PaymentStatusEvent event) {

      Booking booking = bookingRepo.findById(event.getBookingId())
            .orElseThrow(() -> new RuntimeException("Booking not found"));

      Flight flight = flightRepo.findById(booking.getFlightId()).orElseThrow();
      if (event.isPaymentSuccessfull()) {

         flight.setAvailableSeats(flight.getAvailableSeats() - booking.getSeatsBooked());

         booking.setStatus("CONFIRMED");

         flightRepo.save(flight);
         bookingRepo.save(booking);

         BookingEvent checkInEvent = new BookingEvent(booking.getId(), booking.getUserEmail(),
               booking.getFlightNumber(), booking.getSeatsBooked(), booking.getFlightId(), booking.getPrice(),
               booking.getStatus());

         // sending booking details to check in service
         rabbitTemplate.convertAndSend(checkInExchange, checkInRoutingKey, checkInEvent);

      } else {
         booking.setStatus("FAILED");
         bookingRepo.save(booking);
      }

   }
}
