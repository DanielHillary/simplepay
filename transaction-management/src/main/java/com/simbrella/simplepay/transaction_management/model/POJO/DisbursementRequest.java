package com.simbrella.simplepay.transaction_management.model.POJO;

import java.time.LocalDateTime;

public record DisbursementRequest (Double amount, Long loanId, Long accountId, Long creditAccount,
                                    String description, Long issuerId){
}
