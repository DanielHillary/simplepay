package com.simbrella.simplepay.loanmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Loan extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double loanAmount;
    private double interestRate;
    private double repaymentAmount;
    private int loanTerm;
    private int borrowerId;
    private String companyRegNumber;
    private String companyAddress;
    private String state;
    private String area;
    private Double annualRevenue;
    private LocalDate disbursementDate;
    private LocalDate repaymentStartDate;
    private LocalDateTime applicationDate;
    private String status;

}
