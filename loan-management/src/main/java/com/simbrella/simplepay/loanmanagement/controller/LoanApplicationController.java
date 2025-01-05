package com.simbrella.simplepay.loanmanagement.controller;

import com.simbrella.simplepay.loanmanagement.model.POJO.LoanApplicationRequest;
import com.simbrella.simplepay.loanmanagement.model.POJO.StandardResponse;
import com.simbrella.simplepay.loanmanagement.services.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loanapplication")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanService;

    @PostMapping("/applyforloan")
    public ResponseEntity<StandardResponse> applyForLoan(@RequestBody LoanApplicationRequest request){
        return loanService.applyForLoan(request);
    }

    @GetMapping("/getloandetails")
    public ResponseEntity<StandardResponse> getLoanDetails(@RequestParam("id") int id){
        return loanService.getLoanApplicationDetails(id);
    }
}
