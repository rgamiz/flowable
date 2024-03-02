package com.mimacom.demo.loan.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Loan representation")
public class Loan {
    @Schema(description = "Loan ID")
    String id;
    @Schema(description = "Loan Requestor ID")
    String requestorid;
    @Schema(description = "Loan Amount. Double value")
    @NonNull
    Double amount;
    @Schema(description = "Loan term in months. Intreger value")
    @NonNull
    Integer term;
    String status = "processing";
}
