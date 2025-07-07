package com.gj4.urlshortner.service;

import com.gj4.urlshortner.exception.ExpiredLinkException;
import com.gj4.urlshortner.exception.NotFoundException;
import com.gj4.urlshortner.model.ClickLog;
import com.gj4.urlshortner.model.UrlMapping;
import com.gj4.urlshortner.repository.ClickLogRepository;
import com.gj4.urlshortner.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

//TODO
@Service
@RequiredArgsConstructor
public class RedirectServiceImpl implements RedirectService {
    private final UrlMappingRepository urlRepo;
    private final ClickLogRepository clickLogRepo;

    @Override
    public String resolveUrl(String shortCode, String ip, String userAgent) {
        UrlMapping mapping = urlRepo.findByShortCode(shortCode)
                .orElseThrow(() -> new NotFoundException("Short URL not found"));

        if (mapping.getExpiresAt() != null && Instant.now().isAfter(mapping.getExpiresAt())) {
            throw new ExpiredLinkException("Short URL has expired");
        }

        mapping.setClickCount(mapping.getClickCount() + 1);
        urlRepo.save(mapping);

        clickLogRepo.save(ClickLog.builder()
                .id(UUID.randomUUID().toString())
                .shortCode(shortCode)
                .timestamp(Instant.now())
                .ip(ip)
                .userAgent(userAgent)
                .build());

        return mapping.getLongUrl();
    }
}
