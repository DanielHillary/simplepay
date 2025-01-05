package com.simbrella.simplepay.loanmanagement.model.POJO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoanApplicationRequest {
    private double loanAmount;
    private double interestRate;
    private int loanTerm;
    private int borrowerId;
    private String companyRegNumber;
    private String companyAddress;
    private Double annualRevenue;
    private LocalDateTime applicationDate;
    private String decisionNotes;
    private String status;

}
