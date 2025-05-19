package com.example.adminservice.controller;

import com.example.adminservice.entity.Flight;
import com.example.adminservice.service.AdminService;


import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/addFlight")
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody Flight flight){
        return ResponseEntity.ok(adminService.addFlight(flight));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Flight>> getall(){
        return ResponseEntity.ok(adminService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @Valid @RequestBody Flight flight){
       return ResponseEntity.ok(adminService.updateFlight(id, flight));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id){
       
        return ResponseEntity.ok( adminService.delteFlight(id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightByID(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getById(id));
    }

    
}
