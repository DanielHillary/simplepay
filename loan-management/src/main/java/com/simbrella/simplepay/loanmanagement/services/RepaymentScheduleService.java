package com.simbrella.simplepay.loanmanagement.services;

import com.simbrella.simplepay.loanmanagement.exception.ResourceNotFoundException;
import com.simbrella.simplepay.loanmanagement.model.Loan;
import com.simbrella.simplepay.loanmanagement.model.POJO.StandardResponse;
import com.simbrella.simplepay.loanmanagement.model.RepaymentSchedule;
import com.simbrella.simplepay.loanmanagement.repository.RepaymentScheduleRepo;
import com.simbrella.simplepay.loanmanagement.util.LoggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RepaymentScheduleService {
    private final RepaymentScheduleRepo scheduleRepo;
    private final LoggingService loggingService;

    public ResponseEntity<StandardResponse> createRepaymentSchedule(Loan loanDetails) {
        try {
            double repayment = calculateMonthlyRepayment(loanDetails.getLoanAmount(), loanDetails.getInterestRate(), loanDetails.getLoanTerm());
            List<RepaymentSchedule> scheduleList = new ArrayList<>();
            for (int i = 0; i < loanDetails.getLoanTerm(); i++) {
                RepaymentSchedule schedule = new RepaymentSchedule();
                schedule.setAmount(repayment);
                if (i == 0) {
                    schedule.setDueDate(loanDetails.getRepaymentStartDate());
                } else {
                    schedule.setDueDate(scheduleList.get(i - 1).getDueDate().plusDays(30));
                }
                schedule.setInstallmentNumber(++i);
                schedule.setLoanId(loanDetails.getId());
                scheduleList.add(schedule);
            }
            return StandardResponse.sendHttpResponse(true, "Successful", scheduleRepo.saveAll(scheduleList), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static double calculateMonthlyRepayment(double principal, double interestRate, int loanTermMonths) {
        // Convert annual interest rate to monthly rate
        double monthlyInterestRate = interestRate / 12 / 100;
        // Calculate monthly repayment using the formula:
        return (principal * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -loanTermMonths));
    }

    public ResponseEntity<StandardResponse> getPaymentScheduleForLoan(Long id) {
        try {
            List<RepaymentSchedule> scheduleList = scheduleRepo.findByLoanId(id);
            return StandardResponse.sendHttpResponse(true, "Successful", scheduleList, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public ResponseEntity<StandardResponse> updateRepaymentSchedule(RepaymentSchedule schedule) {
        try {

            RepaymentSchedule existingSchedule = scheduleRepo.findById(schedule.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Repayment Schedule not found"));

            existingSchedule.setDueDate(schedule.getDueDate());
            existingSchedule.setAmount(schedule.getAmount());
            existingSchedule.setCompleted(schedule.isCompleted());

            RepaymentSchedule updatedSchedule = scheduleRepo.save(existingSchedule);

            loggingService.logAction("Payment schedule update", String.valueOf(schedule.getId()), "Repayment Schedule was successfully updated");
            return StandardResponse.sendHttpResponse(true, "Repayment schedule successfully updated", updatedSchedule, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return StandardResponse.sendHttpResponse(false, "Could not find schedule", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            loggingService.logError("Error updating repayment schedule", "An error occrured", ex.getMessage());
            StandardResponse response = new StandardResponse(false, "Failed to update repayment schedule", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
