package com.mimacom.demo.loan.controllers;

import com.mimacom.demo.loan.domain.Customer;
import com.mimacom.demo.loan.domain.Loan;
import com.mimacom.demo.loan.domain.LoanManagmentResponse;
import com.mimacom.demo.loan.domain.PaymentReport;
import com.mimacom.demo.loan.exceptions.BadRequestException;
import com.mimacom.demo.loan.exceptions.ResourceNotFoundException;
import com.mimacom.demo.loan.services.LoanManagmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/managment")
public class LoanManagmentRestController {
    @Autowired
    LoanManagmentService loanManagment;

    @PostMapping("/loan/{id}")
    public ResponseEntity<LoanManagmentResponse> startLoanReques(@PathVariable String id) {
        return ResponseEntity.ok(loanManagment.createLoanRequest(id));
    }

    @PostMapping("/loan/{taskId}/customer/data")
    public ResponseEntity<LoanManagmentResponse> saveCustomerData(@PathVariable String taskId,
                                                                  @RequestBody Customer customer) {
        LoanManagmentResponse response = this.loanManagment.saveCustomerData(taskId,customer);
        if (response == null) {
            throw new ResourceNotFoundException("Task with id " + taskId + " not found");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/loan/{taskId}/loan/data")
    public ResponseEntity<LoanManagmentResponse> saveCustomerData(@PathVariable String taskId, @RequestBody Loan loan) {
        LoanManagmentResponse response = loanManagment.saveLoanData(taskId,loan);
        if (response == null) {
            throw new ResourceNotFoundException("Task with id " + taskId + " not found");
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/loan/{taskId}/review/{outcome}")
    public ResponseEntity<LoanManagmentResponse> saveCustomerData(@PathVariable String taskId,@PathVariable String outcome) {
        if(!outcome.equalsIgnoreCase("approved") && !outcome.equalsIgnoreCase("rejected")) {
            throw new BadRequestException("Outcome parameter not valid");
        }
        LoanManagmentResponse response = loanManagment.reviewLoan(taskId,outcome.toLowerCase());
        if (response == null) {
            throw new ResourceNotFoundException("Task with id " + taskId + " not found");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/loan/tasks")
    public ResponseEntity<List<LoanManagmentResponse>> getLoanTasks() {
        log.info("Get loan tasks");
        return ResponseEntity.ok(loanManagment.getActiveTasks("admin"));
    }

    @PostMapping("/loan/payment/report")
    public ResponseEntity<LoanManagmentResponse> getLoanTasks(@RequestBody PaymentReport report) {
        log.info("Save payment report");
        return ResponseEntity.ok(loanManagment.createPaymentReport(report));
    }
}
