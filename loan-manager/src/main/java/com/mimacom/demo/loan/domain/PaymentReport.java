package com.mimacom.demo.loan.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Payment report representation")
public class PaymentReport {
    @Schema(description = "Loan ID")
    String loanId;
    @Schema(description = "Report. Valid values Reliable, Defaulter or Standar. ")
    String report;
}
