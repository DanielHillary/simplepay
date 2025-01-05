package com.simbrella.simplepay.loanmanagement;

import org.springframework.boot.SpringApplication;

public class TestLoanprocessingApplication {

	public static void main(String[] args) {
		SpringApplication.from(LoanManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
