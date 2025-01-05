package com.simbrella.simplepay.loanmanagement.repository;

import com.simbrella.simplepay.loanmanagement.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepo extends JpaRepository<LoanApplication, Integer> {
}
