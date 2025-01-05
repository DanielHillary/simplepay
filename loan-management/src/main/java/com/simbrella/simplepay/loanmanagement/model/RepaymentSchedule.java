package com.simbrella.simplepay.loanmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RepaymentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long loanId;
    private int installmentNumber;
    private LocalDate dueDate;
    private Double amount;
    private boolean isCompleted;
    @OneToOne
    @JoinColumn(name = "loan_repayment_id")
    private LoanRepayment loanRepayment;
}
