package com.simbrella.simplepay.loanmanagement.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TransactionEvent {

    private Long id;
    private Long userId;
    private Double amount;
    private Long loanId;
    private String description;
}
