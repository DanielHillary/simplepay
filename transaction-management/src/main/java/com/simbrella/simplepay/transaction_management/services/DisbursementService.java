package com.simbrella.simplepay.transaction_management.services;

import com.simbrella.simplepay.transaction_management.model.Disbursement;
import com.simbrella.simplepay.transaction_management.model.POJO.DisbursementRequest;
import com.simbrella.simplepay.transaction_management.model.POJO.StandardResponse;
import com.simbrella.simplepay.transaction_management.model.POJO.TransactionRequest;
import com.simbrella.simplepay.transaction_management.model.Transaction;
import com.simbrella.simplepay.transaction_management.repository.DisbursementRepo;
import com.simbrella.simplepay.transaction_management.util.LoggingService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DisbursementService {
    private final DisbursementRepo disbursementRepo;
    private final LoggingService loggingService;
    private final TransactionService transactionService;

    public ResponseEntity<StandardResponse> disburseLoan(DisbursementRequest request) {
        try {
            Disbursement disbursement = new Disbursement();
            TransactionRequest transactionRequest = new TransactionRequest(request.issuerId(), request.amount(),
                    request.loanId(), request.description(), request.creditAccount());
            Transaction transaction = transactionService.processDisbursementTransaction(transactionRequest);
            disbursement.setAmountDisbursed(transaction.getAmount());
            disbursement.setTimeOfDisbursement(LocalDateTime.now());
            disbursement.setLoanId(request.loanId());
            disbursement.setBorrowerId(request.creditAccount());
            disbursement.setIssuerId(request.issuerId());

            disbursementRepo.save(disbursement);

            loggingService.logAction("Loan disbursement", String.valueOf(request.issuerId()), "Disbursed funds for Loan");
            return StandardResponse.sendHttpResponse(true, "Successful", disbursement, HttpStatus.OK);
        } catch (Exception e) {
            loggingService.logError("Error","An error occured", e.getMessage());
            return StandardResponse.sendFailedHttpResponse(false, "Something went wrong");
        }
    }
}
