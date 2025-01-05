package com.simbrella.simplepay.user_management.repository;

import com.simbrella.simplepay.user_management.model.Role;
import com.simbrella.simplepay.user_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String userName);

    Optional<User> findByVerificationOtpAndEmail(String verificationOtp, String email);

    List<User> findAllByRole(Role role);

    Optional<User> findByEmailAndResetOtp(String email, String resetToken);
}
