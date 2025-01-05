package com.simbrella.simplepay.loanmanagement.repository;

import com.simbrella.simplepay.loanmanagement.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatus(String status);

    @Modifying
    @Query(value = "UPDATE loans SET status = :newStatus WHERE id = :loanId", nativeQuery = true)
    int updateLoanStatus(@Param("loanId") Long loanId, @Param("newStatus") String newStatus);
}
