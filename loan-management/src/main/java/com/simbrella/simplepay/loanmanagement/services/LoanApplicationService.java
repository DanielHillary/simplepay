package com.simbrella.simplepay.loanmanagement.services;

import com.simbrella.simplepay.loanmanagement.exception.GlobalExceptionHandler;
import com.simbrella.simplepay.loanmanagement.model.Loan;
import com.simbrella.simplepay.loanmanagement.model.POJO.LoanApplicationRequest;
import com.simbrella.simplepay.loanmanagement.model.POJO.StandardResponse;
import com.simbrella.simplepay.loanmanagement.util.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    public static final int GRADE = 6;
    private final GlobalExceptionHandler exceptionHandler;
    private final LoggingService loggingService;


    public ResponseEntity<StandardResponse> applyForLoan(LoanApplicationRequest request) {
        try {
            Loan loan = new Loan();
            int creditScore = processLoanApplication(request);
            if(creditScore > GRADE){
                //Determine amount to disburse based on credit score
                //Assign to loan officer
            }else{
                return StandardResponse.sendFailedHttpResponse(false, "Sorry, you are not eligle for a loan right now. Please apply after 10 days or speak to our agent");
            }
            loggingService.logAction("Loan application", String.valueOf(request.getBorrowerId()), "User loan application was successful");
            return StandardResponse.sendHttpResponse(true, "Loan application Successful", loan, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return StandardResponse.sendHttpResponse(false, "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private int processLoanApplication(LoanApplicationRequest request) {
        // Connect to external APIs (e.g Credit bureau) to obtain users credit rating
        return 1;
    }

    private void assignToLoanOfficer(LoanApplicationRequest request){
        //Check client state/area and find all officers around the area.
        //Check the loan officer with the least number of applicants assigned
    }

    private BigDecimal determineAmountToDisburse(int creditScore, double loanAmount){
        return BigDecimal.TEN;
    }

    public ResponseEntity<StandardResponse> getLoanApplicationDetails(int id) {
        try {
            return StandardResponse.sendHttpResponse(true, "Successful", HttpStatus.OK);
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "Something went wrong");
        }
    }
}
