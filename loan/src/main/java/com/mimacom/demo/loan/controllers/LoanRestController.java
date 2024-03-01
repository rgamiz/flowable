package com.mimacom.demo.loan.controllers;

import com.mimacom.demo.loan.domain.Customer;
import com.mimacom.demo.loan.domain.Loan;
import com.mimacom.demo.loan.domain.LoanExt;
import com.mimacom.demo.loan.exceptions.BadRequestException;
import com.mimacom.demo.loan.exceptions.ResourceNotFoundException;
import com.mimacom.demo.loan.services.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class LoanRestController {
    @Autowired
    LoanService loanService;

    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getLoans());
    }

    @PostMapping("/loan")
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        log.info("Creating Loan {} ...", loan.getId() );
        this.loanService.saveLoan(loan);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/loan/{id}/status/{status}")
    public ResponseEntity<Loan> updateLoanStatus(@PathVariable() String id, @PathVariable() String status) {
        log.info("Marking Loan {} as {} ...", id, status );
        if(!status.equalsIgnoreCase("approved") && !status.equalsIgnoreCase("rejected")
                && !status.equalsIgnoreCase("processing")) {
            throw new BadRequestException("Status parameter not valid");
        }
        Loan loan = this.loanService.updateLoanStatus(id,status);
        if (loan == null) {
            throw new ResourceNotFoundException("Loan with id " + id + " not found");
        }

        return ResponseEntity.ok(loan);
    }

    @PutMapping("/loan/{id}/paymentreport/{report}")
    public ResponseEntity<LoanExt> updateLoanPaymentStatus(@PathVariable() String id, @PathVariable() String report) {
        log.info("Saving payment report {} as {} ...", id, report );
        if(!report.equalsIgnoreCase("Reliable") && !report.equalsIgnoreCase("Defaulter")
                && !report.equalsIgnoreCase("Standar")) {
            throw new BadRequestException("Status parameter not valid");
        }
        LoanExt loan = this.loanService.updateLoanPaymentStatus(id,report);
        if (loan == null) {
            throw new ResourceNotFoundException("Loan with id " + id + " not found");
        }

        return ResponseEntity.ok(loan);
    }
}
