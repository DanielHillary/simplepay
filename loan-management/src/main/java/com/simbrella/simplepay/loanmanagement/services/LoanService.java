package com.simbrella.simplepay.loanmanagement.services;

import com.simbrella.simplepay.loanmanagement.exception.ResourceNotFoundException;
import com.simbrella.simplepay.loanmanagement.model.Loan;
import com.simbrella.simplepay.loanmanagement.model.POJO.StandardResponse;
import com.simbrella.simplepay.loanmanagement.repository.LoanRepository;
import com.simbrella.simplepay.loanmanagement.util.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    private final LoggingService loggingService;

    public ResponseEntity<StandardResponse> getLoanDetails(Long id) {
        try {
            Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find application"));
            return StandardResponse.sendHttpResponse(true, "Successful", loan, HttpStatus.OK);
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "Somethign went wrong");
        }
    }

    public ResponseEntity<StandardResponse> getLoanByStatus(String status){
        try{
            List<Loan> loanList = loanRepository.findByStatus(status);
            return StandardResponse.sendHttpResponse(true, "Successful", loanList, HttpStatus.OK);
        } catch (Exception e) {
            return StandardResponse.sendHttpResponse(false, "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<StandardResponse> updateLoanStatus(Long id,String status) {
        try {
            loanRepository.updateLoanStatus(id, status);
            loggingService.logAction("Update", String.valueOf(id), "Loan status has been updated");
            return StandardResponse.sendHttpResponse(true, "Update Successful", HttpStatus.OK);
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "Something went wrong. Please retry");
        }
    }
}
