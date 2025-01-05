package com.simbrella.simplepay.user_management.client;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface GatewayClient {

    @GetExchange(value = "/api/v1/auth/encodestring")
    String encodePassword(@RequestParam String details);
}
