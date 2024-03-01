package com.mimacom.demo.loan.services;

import com.mimacom.demo.loan.domain.Customer;
import com.mimacom.demo.loan.persistance.entities.CustomerEntity;
import com.mimacom.demo.loan.persistance.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{
    private List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer("11111111T","nombre1","lastname1", new Date("2001/05/21"),"rgamiz@gmail.com"),
            new Customer("22222222T","nombre2","lastname2", new Date("1940/05/21"),"rgamiz@gmail.com"),
            new Customer("33333333T","nombre3","lastname3", new Date("1978/05/24"),"rgamiz@gmail.com"),
            new Customer("00000000T","nombre4","lastname4", new Date("1985/11/25"),"rgamiz@gmail.com","Defaulter")
    ));

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Optional<Customer> getCustomer(String id) {

        return this.customerRepository.findById(id).map( customerEntity -> {
           Customer customer = new Customer(
                   customerEntity.getId(),
                   customerEntity.getName(),
                   customerEntity.getLastname(),
                   customerEntity.getBirthdate(),
                   customerEntity.getEmail(),
                   customerEntity.getPaymentstatus());

           return customer;
        });

        /*
        return this.customers.stream()
                .filter((c) -> c.getId().equals(id))
                .findFirst()
                .orElse(null);*/
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customerAll = this.customerRepository.findAll().stream().map(customerEntity -> {
            return new Customer(
                    customerEntity.getId(),
                    customerEntity.getName(),
                    customerEntity.getLastname(),
                    customerEntity.getBirthdate(),
                    customerEntity.getEmail(),
                    customerEntity.getPaymentstatus());
        }).collect(Collectors.toList());

        return customerAll;
    }

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity(
                customer.getId(),
                customer.getName(),
                customer.getLastname(),
                customer.getBirthdate(),
                customer.getEmail(),
                customer.getPaymentstatus()
        );
        this.customerRepository.save(customerEntity);
        //this.customers.add(customer);
    }

    public Customer updateCustomer(Customer customer) {
        this.saveCustomer(customer);

        return customer;
    }


}
