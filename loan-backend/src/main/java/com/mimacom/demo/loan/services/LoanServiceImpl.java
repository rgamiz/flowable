package com.mimacom.demo.loan.services;

import com.mimacom.demo.loan.domain.Customer;
import com.mimacom.demo.loan.domain.Loan;
import com.mimacom.demo.loan.domain.LoanExt;
import com.mimacom.demo.loan.persistance.entities.LoanEntity;
import com.mimacom.demo.loan.persistance.repositories.CustomerRepository;
import com.mimacom.demo.loan.persistance.repositories.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoanServiceImpl implements LoanService{
    private List<Loan> loans = new ArrayList<>(Arrays.asList(
            new Loan(1500000.0,36),
            new Loan(2500000.0,48)
    ));

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void saveLoan(Loan loan) {
        log.info("Saving loan: {}", loan);

        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setId(loan.getId());
        loanEntity.setAmount(loan.getAmount());
        loanEntity.setTerm(loan.getTerm());
        loanEntity.setStatus(loan.getStatus());
        loanEntity.setRequestor(customerRepository.findById(loan.getRequestorid()).orElse(null));

        loanRepository.save(loanEntity);

        //this.loans.add(loan);
    }

    @Override
    public List<Loan> getLoans() {
        log.info("Get loans");
        List<Loan> loansAll = loanRepository.findAll().stream().map(loanEntity -> {
            return new Loan(
                    loanEntity.getId(),
                    loanEntity.getRequestor() != null?loanEntity.getRequestor().getId():null,
                    loanEntity.getAmount(),
                    loanEntity.getTerm(),
                    loanEntity.getStatus()
            );
        }).collect(Collectors.toList());

        return loansAll;
    }

    @Override
    public Loan updateLoanStatus(String id, String status) {
        Loan loan = null;
        LoanEntity loanEntity = this.loanRepository.findById(id).orElse(null);
        if(loanEntity != null) {
            loanEntity.setStatus(status);
            this.loanRepository.save(loanEntity);

            loan = new Loan(
                    loanEntity.getId(),
                    loanEntity.getRequestor() != null?loanEntity.getRequestor().getId():null,
                    loanEntity.getAmount(),
                    loanEntity.getTerm(),
                    loanEntity.getStatus()
            );
        }

        return loan;
    }

    public LoanExt updateLoanPaymentStatus(String id, String report) {
        LoanExt loan = null;
        LoanEntity loanEntity = this.loanRepository.findById(id).orElse(null);
        if(loanEntity != null && loanEntity.getRequestor() != null) {
            Customer customer = this.customerService.getCustomer(loanEntity.getRequestor().getId()).orElse(null);

            if(customer != null) {
                customer.setPaymentstatus(report);
                this.customerService.updateCustomer(customer);
            }

            loan = new LoanExt(loanEntity.getId(),loanEntity.getAmount(),loanEntity.getTerm(),loanEntity.getStatus(),customer);
        }

        return loan;
    }
}
