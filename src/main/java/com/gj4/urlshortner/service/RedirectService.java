package com.gj4.urlshortner.service;

public interface RedirectService {
    String resolveUrl(String shortCode, String ip, String userAgent);
}
