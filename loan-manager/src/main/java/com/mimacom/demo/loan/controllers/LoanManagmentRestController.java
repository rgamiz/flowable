package com.mimacom.demo.loan.controllers;

import com.mimacom.demo.loan.domain.Customer;
import com.mimacom.demo.loan.domain.Loan;
import com.mimacom.demo.loan.domain.LoanManagmentResponse;
import com.mimacom.demo.loan.domain.PaymentReport;
import com.mimacom.demo.loan.exceptions.BadRequestException;
import com.mimacom.demo.loan.exceptions.ResourceNotFoundException;
import com.mimacom.demo.loan.services.LoanManagmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/managment")
@Tag(name = "Loan Manager",
        description = "Manage Loan Request and Loan proccess")
public class LoanManagmentRestController {
    @Autowired
    LoanManagmentService loanManagment;

    @ApiResponse(responseCode = "200", description = "Process created")
    @Operation(summary = "Create a loan", description = "Create loan request for a specific client")
    @PostMapping("/loan/{id}")
    public ResponseEntity<LoanManagmentResponse> startLoanReques(
            @Parameter(description = "Customer ID key in DB", required = true, example = "00000000T")
            @PathVariable String id) {
        return ResponseEntity.ok(loanManagment.createLoanRequest(id));
    }

    @ApiResponse(responseCode = "200", description = "Information send")
    @ApiResponse(responseCode = "404", description = "Task not exist")
    @Operation(summary = "Fill customer data", description = "Fill in customer details if we not have it")
    @PostMapping("/loan/{taskId}/customer/data")
    public ResponseEntity<LoanManagmentResponse> saveCustomerData(
            @Parameter(description = "Task ID active in process", required = true)
            @PathVariable String taskId,
            @RequestBody Customer customer) {
        LoanManagmentResponse response = this.loanManagment.saveCustomerData(taskId,customer);
        if (response == null) {
            throw new ResourceNotFoundException("Task with id " + taskId + " not found");
        }

        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "200", description = "Information send")
    @ApiResponse(responseCode = "404", description = "Task not exist")
    @Operation(summary = "Fill loan data", description = "Fill in loan details the client es requesting")
    @PostMapping("/loan/{taskId}/loan/data")
    public ResponseEntity<LoanManagmentResponse> saveCustomerData(
            @Parameter(description = "ID of a task active in process", required = true)
            @PathVariable String taskId, @RequestBody Loan loan) {
        LoanManagmentResponse response = loanManagment.saveLoanData(taskId,loan);
        if (response == null) {
            throw new ResourceNotFoundException("Task with id " + taskId + " not found");
        }

        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "200", description = "Information send")
    @ApiResponse(responseCode = "404", description = "Task not exist")
    @Operation(summary = "Review loan outcome", description = "Save result of loan and customer information revision")
    @PutMapping("/loan/{taskId}/review/{outcome}")
    public ResponseEntity<LoanManagmentResponse> saveCustomerData(
            @Parameter(description = "ID of a task active in process", required = true)
            @PathVariable String taskId,@PathVariable String outcome) {
        if(!outcome.equalsIgnoreCase("approved") && !outcome.equalsIgnoreCase("rejected")) {
            throw new BadRequestException("Outcome parameter not valid");
        }
        LoanManagmentResponse response = loanManagment.reviewLoan(taskId,outcome.toLowerCase());
        if (response == null) {
            throw new ResourceNotFoundException("Task with id " + taskId + " not found");
        }

        return ResponseEntity.ok(response);
    }

    @ApiResponse(responseCode = "200", description = "Task query ended ok")
    @Operation(summary = "Get tasks", description = "Ged admin active tasks info")
    @GetMapping("/loan/tasks")
    public ResponseEntity<List<LoanManagmentResponse>> getLoanTasks() {
        log.info("Get loan tasks");
        return ResponseEntity.ok(loanManagment.getActiveTasks("admin"));
    }

    @ApiResponse(responseCode = "200", description = "Create payment report ")
    @Operation(summary = "Create a payment repor", description = "Create payment report process")
    @PostMapping("/loan/payment/report")
    public ResponseEntity<LoanManagmentResponse> getLoanTasks(@RequestBody PaymentReport report) {
        log.info("Save payment report");
        return ResponseEntity.ok(loanManagment.createPaymentReport(report));
    }
}
