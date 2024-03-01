package com.mimacom.demo.loan.domain;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {
    @NonNull
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String lastname;
    @NonNull
    private Date birthdate;
    @NonNull
    private String email;

    private String paymentstatus = "Standard";

    public Customer(String id) {
        this.id = id;
    }
}
