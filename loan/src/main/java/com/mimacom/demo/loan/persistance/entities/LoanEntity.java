package com.mimacom.demo.loan.persistance.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="loan")
@Data
@NoArgsConstructor
public class LoanEntity {
    @Id
    String id;

    @ManyToOne()
    @JoinColumn(name = "requestorid", referencedColumnName = "id")
    CustomerEntity requestor;

    Double amount;
    Integer term;
    String status = "On review";
}
