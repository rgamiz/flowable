package com.mimacom.demo.loan.services;

import com.mimacom.demo.loan.domain.Customer;
import com.mimacom.demo.loan.domain.Loan;
import com.mimacom.demo.loan.domain.LoanManagmentResponse;
import com.mimacom.demo.loan.domain.PaymentReport;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoanManagmentServiceFlowableImpl implements LoanManagmentService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;

    @Override
    public LoanManagmentResponse createLoanRequest(String id) {
        identityService.setAuthenticatedUserId("admin");

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("requestorId", id);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("loanRequestProcess"
                , variables);

        LoanManagmentResponse response = new LoanManagmentResponse(processInstance.getProcessInstanceId(),null, null);

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).taskAssignee("admin").active()
                .list().stream().findFirst().orElse(null);
        if(task != null) {
            showVariables(processInstance.getId());
            response.setNextTaskId(task.getId());
            response.setNextTaskName(task.getName());
        }

        return response;
    }

    @Override
    public LoanManagmentResponse saveCustomerData(String taskId, Customer customer) {
        identityService.setAuthenticatedUserId("admin");
        DateFormat dfdt = new SimpleDateFormat("yyyy-MM-dd");

        LoanManagmentResponse response = null;

        Task task = taskService.createTaskQuery().taskId(taskId)
                .taskCandidateOrAssigned("admin").active().list().stream().findFirst()
                .orElse(null);
        if(task != null) {
            response = new LoanManagmentResponse(task.getProcessInstanceId(),null, null);

            log.info("{} y {}", task.getTaskDefinitionKey(), task.getTaskDefinitionId());
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("requestorName", customer.getName());
            variables.put("requestorLastName", customer.getLastname());
            variables.put("requestorBirthDate", dfdt.format(customer.getBirthdate()));
            variables.put("requestorEmail", customer.getEmail());

            taskService.complete(task.getId(), "admin", variables);

            task = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId())
                    .taskCandidateOrAssigned("admin").active().list().stream().findFirst()
                    .orElse(null);

            if(task != null) {
                response.setNextTaskId(task.getId());
                response.setNextTaskName(task.getName());

                showVariables(task.getProcessInstanceId());
            }
        }

        return response;
    }

    @Override
    public LoanManagmentResponse saveLoanData(String taskId, Loan loan) {
        identityService.setAuthenticatedUserId("admin");

        LoanManagmentResponse response = null;

        Task task = taskService.createTaskQuery().taskId(taskId).taskCandidateOrAssigned("admin").active()
                .list().stream().findFirst().orElse(null);
        if(task != null) {
            response = new LoanManagmentResponse(task.getProcessInstanceId(),null, null);

            log.info("{} y {}", task.getTaskDefinitionKey(), task.getTaskDefinitionId());
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("loanAmount", loan.getAmount());
            variables.put("loanTerm", loan.getTerm());

            taskService.complete(task.getId(), "admin", variables);

            task = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId())
                    .taskCandidateOrAssigned("admin").active().list().stream().findFirst()
                    .orElse(null);

            if(task != null) {
                response.setNextTaskId(task.getId());
                response.setNextTaskName(task.getName());

                showVariables(task.getProcessInstanceId());
            }
        }

        return response;
    }

    @Override
    public LoanManagmentResponse reviewLoan(String taskId, String outcome) {
        identityService.setAuthenticatedUserId("admin");
        LoanManagmentResponse response = null;
        Task task = taskService.createTaskQuery().taskId(taskId).taskCandidateOrAssigned("admin").active()
                .list().stream().findFirst().orElse(null);
        if(task != null) {
            response = new LoanManagmentResponse(task.getProcessInstanceId(),null, null);

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("loanReviewOutcome", outcome);
            taskService.complete(task.getId(), "admin", variables);

            task = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId())
                    .taskCandidateOrAssigned("admin").active().list().stream().findFirst()
                    .orElse(null);

            if(task != null) {
                response.setNextTaskId(task.getId());
                response.setNextTaskName(task.getName());

                showVariables(task.getProcessInstanceId());
            }
        }

        return response;
    }

    @Override
    public List<LoanManagmentResponse> getActiveTasks(String username) {
        List<LoanManagmentResponse> response = new ArrayList<LoanManagmentResponse>();

        response = taskService.createTaskQuery().taskCandidateOrAssigned(username).active()
                .list().stream().map((task) -> new LoanManagmentResponse(task.getProcessInstanceId(), task.getId(),
                        task.getName())).collect(Collectors.toList());

        return response;
    }

    @Override
    public LoanManagmentResponse createPaymentReport(PaymentReport report) {
        identityService.setAuthenticatedUserId("admin");

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("loanId", report.getLoanId());
        variables.put("paymentStatus",report.getReport());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("paymentReportProcess"
                , variables);

        LoanManagmentResponse response = new LoanManagmentResponse(processInstance.getProcessInstanceId(),null, null);

        return response;
    }

    private void showVariables(String excutionId) {
        Map<String,Object> variables = runtimeService.getVariables(excutionId);

        for(Map.Entry<String,Object> entry : variables.entrySet()){
            log.info(entry.getKey()+"="+entry.getValue());
        }
    }
}
