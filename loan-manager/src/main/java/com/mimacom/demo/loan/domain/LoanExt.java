package com.mimacom.demo.loan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanExt {
    String id;
    Double amount;
    Integer term;
    String status;
    private Customer customer;
}
