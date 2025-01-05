package com.simbrella.simplepay.transaction_management.repository;

import com.simbrella.simplepay.transaction_management.model.Disbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisbursementRepo extends JpaRepository<Disbursement, Long> {
}
