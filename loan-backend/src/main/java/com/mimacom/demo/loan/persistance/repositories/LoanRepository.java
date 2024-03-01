package com.mimacom.demo.loan.persistance.repositories;

import com.mimacom.demo.loan.persistance.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, String> {
}
