package com.simbrella.simplepay.loanmanagement.controller;

import com.simbrella.simplepay.loanmanagement.model.Loan;
import com.simbrella.simplepay.loanmanagement.model.POJO.StandardResponse;
import com.simbrella.simplepay.loanmanagement.model.RepaymentSchedule;
import com.simbrella.simplepay.loanmanagement.services.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repaymentschedule")
public class RepaymentScheduleController {

    private final RepaymentScheduleService scheduleService;

    @PostMapping("/createpaymentschedule")
    public ResponseEntity<StandardResponse> createSchedule(@RequestBody Loan loanDetails){
        return scheduleService.createRepaymentSchedule(loanDetails);
    }

    @GetMapping("/getpaymentscheduleforloan")
    public ResponseEntity<StandardResponse> getScheduleForLoan(@RequestParam("id") Long id){
        return scheduleService.getPaymentScheduleForLoan(id);
    }

    @PutMapping("/updatepaymentschedule")
    public ResponseEntity<StandardResponse> updatePaymentSchedule(@RequestBody RepaymentSchedule schedule){
        return scheduleService.updateRepaymentSchedule(schedule);
    }
}
