package com.simbrella.simplepay.loanmanagement.repository;

import com.simbrella.simplepay.loanmanagement.model.RepaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepaymentScheduleRepo extends JpaRepository<RepaymentSchedule, Long> {
    List<RepaymentSchedule> findByLoanId(Long id);
}
