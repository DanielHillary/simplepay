package com.simbrella.simplepay.transaction_management.event;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TransactionEvent implements Serializable {

    private Long id;
    private Long userId;
    private Double amount;
    private Long loanId;
    private String description;
}