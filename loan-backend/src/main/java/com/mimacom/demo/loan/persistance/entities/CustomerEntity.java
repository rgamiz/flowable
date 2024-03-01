package com.mimacom.demo.loan.persistance.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name="customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    private String id;

    private String name;
    private String lastname;
    private Date birthdate;
    private String email;
    private String paymentstatus;
}
