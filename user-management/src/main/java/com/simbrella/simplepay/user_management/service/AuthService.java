package com.simbrella.simplepay.user_management.service;

import com.simbrella.simplepay.user_management.model.Token;
import com.simbrella.simplepay.user_management.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenRepository tokenRepository;

    public List<Token> getAllValidTokenForUser(int id) {
        return tokenRepository.findAllValidTokenByUser(id);
    }

    public Optional<Token> getTokenByJwt(String jwt) {
        return tokenRepository.findByToken(jwt);
    }

    public void saveToken(Token token) {
        tokenRepository.save(token);
    }
}
