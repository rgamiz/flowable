package com.mimacom.demo.loan.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response of managment services")
public class LoanManagmentResponse {
    @Schema(description = "Id of the instance and Loan ID")
    String processInstanceId;
    @Schema(description = "Next task active ID")
    String nextTaskId;
    @Schema(description = "Next task active Name")
    String nextTaskName;
}
