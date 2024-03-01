package com.mimacom.demo.loan.controllers;

import com.mimacom.demo.loan.domain.Customer;
import com.mimacom.demo.loan.exceptions.ResourceNotFoundException;
import com.mimacom.demo.loan.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class CustomerRestController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        log.info("Searching {} ...", id );
        Customer customer = this.customerService.getCustomer(id).orElse(null);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with id " + id + " not found");
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(this.customerService.getCustomers());
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        log.info("Creating {} ...", customer.getId() );
        this.customerService.saveCustomer(customer);
        return ResponseEntity.ok(customer);
    }
}
