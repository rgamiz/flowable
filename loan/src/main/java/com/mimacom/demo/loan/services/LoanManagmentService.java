package com.mimacom.demo.loan.services;

import com.mimacom.demo.loan.domain.Customer;
import com.mimacom.demo.loan.domain.Loan;
import com.mimacom.demo.loan.domain.LoanManagmentResponse;
import com.mimacom.demo.loan.domain.PaymentReport;

import java.util.List;

public interface LoanManagmentService {
    public LoanManagmentResponse createLoanRequest(String id);

    public LoanManagmentResponse saveCustomerData(String id, Customer customer);

    public LoanManagmentResponse saveLoanData(String taskId, Loan loan);

    public LoanManagmentResponse reviewLoan(String taskId, String outcome);

    public List<LoanManagmentResponse> getActiveTasks(String username);

    public LoanManagmentResponse createPaymentReport(PaymentReport report);
}
