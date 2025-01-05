package com.simbrella.simplepay.loanmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRepayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amountRepaid;
    private Long loanId;
    private Double remainderAmount;
    private LocalDateTime repaymentTime;
    @OneToOne(mappedBy = "loanRepayment")
    private RepaymentSchedule schedule;
    private boolean isFullPayment;
}
