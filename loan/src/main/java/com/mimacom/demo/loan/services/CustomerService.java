package com.mimacom.demo.loan.services;

import com.mimacom.demo.loan.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    public Optional<Customer> getCustomer(String id);

    public List<Customer> getCustomers();

    public void saveCustomer(Customer customer);

    public Customer updateCustomer(Customer customer);
}
