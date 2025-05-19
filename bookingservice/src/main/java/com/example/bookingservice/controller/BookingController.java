package com.example.bookingservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingservice.model.Booking;
import com.example.bookingservice.service.BookingService;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/booking")

public class BookingController {
  
  @Autowired
  private BookingService bookingService;

 @PostMapping("/book")
  public ResponseEntity<Booking>bookFight(@RequestParam Long flightId,
  @RequestParam String email,
  @RequestParam int seats){
  return ResponseEntity.ok(bookingService.bookFlight(flightId, email, seats));
  }


  @GetMapping("/myBooking")
  public ResponseEntity<List<Booking>> booking(@RequestParam String email){
  return ResponseEntity.ok(bookingService.getBooking(email));
  }




}
