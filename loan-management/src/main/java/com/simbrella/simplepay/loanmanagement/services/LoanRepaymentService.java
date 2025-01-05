package com.simbrella.simplepay.loanmanagement.services;

import com.simbrella.simplepay.loanmanagement.event.TransactionEvent;
import com.simbrella.simplepay.loanmanagement.model.LoanRepayment;
import com.simbrella.simplepay.loanmanagement.model.POJO.StandardResponse;
import com.simbrella.simplepay.loanmanagement.repository.LoanRepaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanRepaymentService {

    private final LoanRepaymentRepo repaymentRepo;

    @KafkaListener(topics = "notification-completed")
    public void listen(TransactionEvent transactionEvent){
        LoanRepayment repayment = new LoanRepayment();
        double amountToPay = repayment.getSchedule().getAmount();
        repayment.setAmountRepaid(transactionEvent.getAmount());
        repayment.setRemainderAmount(transactionEvent.getAmount() - amountToPay);
        repayment.setFullPayment(repayment.getRemainderAmount() <= 0);
        repayment.setLoanId(transactionEvent.getLoanId());

        repaymentRepo.save(repayment);
    }

    public ResponseEntity<StandardResponse> getLoanRepayments(Long id){
        try {
            List<LoanRepayment> repaymentList = repaymentRepo.findByLoanId(id);
            return StandardResponse.sendHttpResponse(true, "Successful", repaymentList, HttpStatus.OK);
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "Something went wrong");
        }
    }


}
