package com.simbrella.simplepay.api_gateway.service;


import com.simbrella.simplepay.api_gateway.POJO.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {
    private final RateLimiter rateLimiter;

    public RateLimiterService() {
        // Allow max 10 requests per minute per IP address
        this.rateLimiter = new RateLimiter(10, 60 * 1000);
    }

    public boolean isRequestAllowed(String identifier) {
        return rateLimiter.isAllowed(identifier);
    }
}
