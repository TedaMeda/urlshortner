package com.gj4.urlshortner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimiterServiceImpl implements RateLimiterService {
    private final StringRedisTemplate redisTemplate;

    private final int maxTokens = 10;
    private final Duration refillInterval = Duration.ofMinutes(1);

    @Override
    public boolean isAllowed(String clientId) {
        String key = "rate:" + clientId;
        Long current = redisTemplate.opsForValue().increment(key);

        if (current == 1) {
            redisTemplate.expire(key, refillInterval);
        }

        return current <= maxTokens;
    }
}
