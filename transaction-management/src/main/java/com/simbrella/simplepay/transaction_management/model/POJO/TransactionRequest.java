package com.simbrella.simplepay.transaction_management.model.POJO;

public record TransactionRequest(Long userId, double amount, Long loanId,
                                 String description, Long creditAccount) {
}
