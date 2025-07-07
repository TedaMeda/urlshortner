package com.gj4.urlshortner.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class Base62Encoder {
    private static final String CHARSET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = CHARSET.length();
    private final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    public String generateShortCode() {
        long num = counter.getAndIncrement();
        return encode(num);
    }

    public String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(CHARSET.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    public long decode(String shortCode) {
        long num = 0;
        for (char c : shortCode.toCharArray()) {
            num = num * BASE + CHARSET.indexOf(c);
        }
        return num;
    }
}

