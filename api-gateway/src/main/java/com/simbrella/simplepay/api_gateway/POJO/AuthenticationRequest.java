package com.simbrella.simplepay.api_gateway.POJO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthenticationRequest {

    private String email;
    String password;
}
