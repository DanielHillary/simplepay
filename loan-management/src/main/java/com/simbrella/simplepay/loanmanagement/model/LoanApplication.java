package com.simbrella.simplepay.loanmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplication extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double loanAmount;
    private double interestRate;
    private int loanTerm;
    private int borrowerId;
    private String state;
    private String area;
    private String companyRegNumber;
    private String companyAddress;
    private Double annualRevenue;
    private LocalDateTime applicationDate;
    private String decisionNotes;
    private String status;

}
