package com.mimacom.demo.loan.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Loan {
    String id;
    String requestorid;
    @NonNull
    Double amount;
    @NonNull
    Integer term;
    String status = "processing";
}
