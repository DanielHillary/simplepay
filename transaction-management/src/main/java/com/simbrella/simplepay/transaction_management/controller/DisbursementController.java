package com.simbrella.simplepay.transaction_management.controller;

import com.simbrella.simplepay.transaction_management.model.POJO.DisbursementRequest;
import com.simbrella.simplepay.transaction_management.model.POJO.StandardResponse;
import com.simbrella.simplepay.transaction_management.services.DisbursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/disbursement")
public class DisbursementController {

    private final DisbursementService disbursementService;

    @PostMapping("/disburseloan")
    public ResponseEntity<StandardResponse> disburseLoan(@RequestBody DisbursementRequest request){
        return disbursementService.disburseLoan(request);
    }
}
