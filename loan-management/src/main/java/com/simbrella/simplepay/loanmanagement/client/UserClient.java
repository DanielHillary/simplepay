package com.simbrella.simplepay.loanmanagement.client;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface UserClient {

    @GetExchange(value = "/api/v1/user")
    void getUserById(@RequestParam Long id);
}
