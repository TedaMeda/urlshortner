package com.gj4.urlshortner.service;

public interface RateLimiterService {
    boolean isAllowed(String clientId);
}
