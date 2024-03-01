package com.mimacom.demo.loan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanManagmentResponse {
    String processInstanceId;
    String nextTaskId;
    String nextTaskName;
}
