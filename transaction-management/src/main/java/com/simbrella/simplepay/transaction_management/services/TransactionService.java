package com.simbrella.simplepay.transaction_management.services;

import com.simbrella.simplepay.transaction_management.event.TransactionEvent;
import com.simbrella.simplepay.transaction_management.model.Account;
import com.simbrella.simplepay.transaction_management.model.POJO.StandardResponse;
import com.simbrella.simplepay.transaction_management.model.POJO.TransactionRequest;
import com.simbrella.simplepay.transaction_management.model.Transaction;
import com.simbrella.simplepay.transaction_management.repository.AccountRepository;
import com.simbrella.simplepay.transaction_management.repository.TransactionRepository;
import com.simbrella.simplepay.transaction_management.util.LoggingService;
import com.simbrella.simplepay.transaction_management.util.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PdfGenerator pdfGenerator;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;
    private final LoggingService loggingService;

    private static final double tierOne = 10000.00;
    private static final double tierTwo = 25000.00;
    private static final double tierThree = 100000.00;

    public byte[] processTransaction(TransactionRequest request) {
        try {
            //Add Charges
            Double amount = request.amount();
            if (amount == 0) {
                throw new IllegalArgumentException("Transaction amount must be greater than zero.");
            } else if (amount < tierOne) {
                amount -= 10.00;
            } else if (amount < tierTwo && amount > tierOne) {
                amount -= 25.00;
            }

            Account account = accountRepository.findByUserId(request.userId())
                    .orElseThrow(() -> new IllegalArgumentException("Account not found for user ID: " + request.userId()));

            // Check if the transaction is a debit and validate balance
            if (amount < 0 && account.getBalance() + amount < 0) {
                throw new IllegalArgumentException("Insufficient funds for this transaction.");
            }

            //Transfer successful
            Transaction transaction = Transaction.builder()
                    .debitAccountId(request.userId())
                    .creditAccountId(request.creditAccount())
                    .amount(amount)
                    .description(request.description())
                    .transactionTime(LocalDateTime.now())
                    .build();
            Transaction savedTransaction = transactionRepository.save(transaction);

            TransactionEvent event = new TransactionEvent(savedTransaction.getId(), request.userId(), request.amount(), request.loanId(), request.description());

            kafkaTemplate.send("transaction-completed", event);

            // Update account balance
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);

            loggingService.logAction("Transaction successful", String.valueOf(transaction.getId()), "Transfer to " + account.getAccountNumber() + " was successful");

            return pdfGenerator.generateSingleTransactionPdf(transaction);
        } catch (Exception e) {
            loggingService.logError("Error", "AN ERROR OCCURED", e.getMessage());
            return null;
        }
    }

    public ResponseEntity<StandardResponse> getUserTransactions(Long userId, Pageable pageable) {
        try {
            return StandardResponse.sendHttpResponse(true, "Successful", transactionRepository.findByUserId(userId, pageable).getContent(), HttpStatus.OK);
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "Something went wrong");
        }

    }

    public Transaction processDisbursementTransaction(TransactionRequest request) {
        try {
            //Add Charges
            Double amount = request.amount();
            if (amount == 0) {
                throw new IllegalArgumentException("Transaction amount must be greater than zero.");
            } else if (amount < tierOne) {
                amount -= 10.00;
            } else if (amount < tierTwo && amount > tierOne) {
                amount -= 25.00;
            }

            Account account = accountRepository.findByUserId(request.userId())
                    .orElseThrow(() -> new IllegalArgumentException("Account not found for user ID: " + request.userId()));

            // Check if the transaction is a debit and validate balance
            if (amount < 0 && account.getBalance() + amount < 0) {
                throw new IllegalArgumentException("Insufficient funds for this transaction.");
            }

            //Transfer successful
            Transaction transaction = Transaction.builder()
                    .debitAccountId(request.userId())
                    .creditAccountId(request.creditAccount())
                    .amount(amount)
                    .description(request.description())
                    .transactionTime(LocalDateTime.now())
                    .build();

            // Update account balance
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);

            loggingService.logAction("Transaction successful", String.valueOf(transaction.getId()), "Transfer to " + account.getAccountNumber() + " was successful");

            return transactionRepository.save(transaction);
        } catch (Exception e) {

            loggingService.logError("Error", "An error occured", "Transaction failed");
            return null;
        }
    }

}

