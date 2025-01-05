package com.simbrella.simplepay.transaction_management;

import org.springframework.boot.SpringApplication;

public class TestTransactionManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(TransactionManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
