package com.simbrella.simplepay.loanmanagement.repository;

import com.simbrella.simplepay.loanmanagement.model.LoanRepayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepaymentRepo extends JpaRepository<LoanRepayment, Long> {
    List<LoanRepayment> findByLoanId(Long id);
}
