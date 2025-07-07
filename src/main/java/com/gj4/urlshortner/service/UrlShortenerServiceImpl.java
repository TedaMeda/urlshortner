package com.gj4.urlshortner.service;

import com.gj4.urlshortner.model.UrlMapping;
import com.gj4.urlshortner.repository.UrlMappingRepository;
import com.gj4.urlshortner.utils.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final UrlMappingRepository urlRepo;
    private final Base62Encoder encoder;

    @Override
    public String shortenUrl(String longUrl, String customAlias, Instant expiresAt) {
        String shortCode = (customAlias != null) ? customAlias : encoder.generateShortCode();
        if (urlRepo.existsByShortCode(shortCode)) {
            throw new IllegalArgumentException("Short code already exists.");
        }

        UrlMapping mapping = UrlMapping.builder()
                .id(UUID.randomUUID().toString())
                .shortCode(shortCode)
                .longUrl(longUrl)
                .createdAt(Instant.now())
                .expiresAt(expiresAt)
                .clickCount(0L)
                .build();

        urlRepo.save(mapping);
        return shortCode;
    }
}
