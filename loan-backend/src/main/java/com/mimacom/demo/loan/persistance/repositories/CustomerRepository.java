package com.mimacom.demo.loan.persistance.repositories;

import com.mimacom.demo.loan.persistance.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

}
