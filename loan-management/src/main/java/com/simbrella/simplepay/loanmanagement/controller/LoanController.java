package com.simbrella.simplepay.loanmanagement.controller;

import com.simbrella.simplepay.loanmanagement.model.POJO.StandardResponse;
import com.simbrella.simplepay.loanmanagement.repository.LoanRepository;
import com.simbrella.simplepay.loanmanagement.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/getloandetails")
    public ResponseEntity<StandardResponse> getLoanDetails(@RequestParam("id") Long id){
        return loanService.getLoanDetails(id);
    }

    @PutMapping("/updateloanstatus")
    public ResponseEntity<StandardResponse> updateLoadStatus(@RequestParam("id") Long id, @RequestParam("status") String status){
        return loanService.updateLoanStatus(id, status);
    }

    @GetMapping("/getloanbystatus")
    public ResponseEntity<StandardResponse> getLoanByStatus(@RequestParam("status") String status){
        return loanService.getLoanByStatus(status);
    }
}
