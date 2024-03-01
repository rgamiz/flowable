package com.mimacom.demo.loan.services;

import com.mimacom.demo.loan.domain.Customer;
import com.mimacom.demo.loan.domain.Loan;
import com.mimacom.demo.loan.domain.LoanExt;

import java.util.List;

public interface LoanService {
    public void saveLoan(Loan loan);
    public List<Loan> getLoans();
    public Loan updateLoanStatus(String id, String status);
    public LoanExt updateLoanPaymentStatus(String id, String report);
}
