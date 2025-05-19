package com.example.adminservice.repo;

import com.example.adminservice.entity.Flight;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepo extends JpaRepository<Flight,Long> {
 
}
