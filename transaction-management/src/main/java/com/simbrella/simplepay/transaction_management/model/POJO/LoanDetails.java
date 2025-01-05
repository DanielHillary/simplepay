package com.simbrella.simplepay.transaction_management.model.POJO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class LoanDetails {
    private double principalAmount;
    private double interestRate;
    private int loanTerm;
    private Long loanId;
    private LocalDate startDate;
}
