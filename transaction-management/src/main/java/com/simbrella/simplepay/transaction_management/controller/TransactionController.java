package com.simbrella.simplepay.transaction_management.controller;

import com.simbrella.simplepay.transaction_management.model.POJO.StandardResponse;
import com.simbrella.simplepay.transaction_management.model.POJO.TransactionRequest;
import com.simbrella.simplepay.transaction_management.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/processpayment")
    public ResponseEntity<byte[]> processTransaction(@RequestBody TransactionRequest request) {
        byte[] pdf = transactionService.processTransaction(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transaction_statement.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/getusertransactions")
    public ResponseEntity<StandardResponse> getUserTransactions(@RequestParam("id") Long id){
        return transactionService.getUserTransactions(id, Pageable.ofSize(15));
    }
}
