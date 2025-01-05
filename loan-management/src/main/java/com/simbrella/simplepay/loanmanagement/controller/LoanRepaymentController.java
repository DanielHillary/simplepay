package com.simbrella.simplepay.loanmanagement.controller;

import com.simbrella.simplepay.loanmanagement.model.POJO.StandardResponse;
import com.simbrella.simplepay.loanmanagement.services.LoanRepaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/loan/loanrepayement")
public class LoanRepaymentController {

    private final LoanRepaymentService loanRepaymentService;

    @GetMapping("/getallloanrepayment")
    public ResponseEntity<StandardResponse> getUserRepayment(@RequestParam("loanId") Long loanId){
        return loanRepaymentService.getLoanRepayments(loanId);
    }
}
