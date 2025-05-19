package com.example.bookingservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingservice.model.Booking;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Long> {
  
  List<Booking> findByUserEmail(String email);
}
