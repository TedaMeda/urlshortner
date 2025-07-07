package com.gj4.urlshortner.service;

import com.gj4.urlshortner.repository.UrlMappingRepository;
import com.gj4.urlshortner.utils.Base62Encoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceImplTest {

    @Mock
    private UrlMappingRepository urlRepo;

    @Mock
    private Base62Encoder encoder;

    @InjectMocks
    private UrlShortenerServiceImpl service;

    @Test
    void shouldShortenUrl_withGeneratedAlias() {
        String longUrl = "https://example.com";
        String shortCode = "abc123";

        when(encoder.generateShortCode()).thenReturn(shortCode);
        when(urlRepo.existsByShortCode(shortCode)).thenReturn(false);

        String result = service.shortenUrl(longUrl, null, null);

        assertEquals(shortCode, result);
        verify(urlRepo).save(any());
    }
}