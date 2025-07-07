package com.gj4.urlshortner.service;

import java.time.Instant;

public interface UrlShortenerService {
    String shortenUrl(String longUrl, String customAlias, Instant expiresAt);
}
