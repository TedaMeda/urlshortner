package com.gj4.urlshortner.controller;

import com.gj4.urlshortner.service.AnalyticsService;
import com.gj4.urlshortner.service.RateLimiterService;
import com.gj4.urlshortner.service.RedirectService;
import com.gj4.urlshortner.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UrlController.class)
@Import(UrlControllerTest.TestConfig.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UrlShortenerService shortenerService;

    @Autowired
    private RedirectService redirectService;

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private RateLimiterService rateLimiterService;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public UrlShortenerService shortenerService() {
            return Mockito.mock(UrlShortenerService.class);
        }

        @Bean
        public RedirectService redirectService() {
            return Mockito.mock(RedirectService.class);
        }

        @Bean
        public AnalyticsService analyticsService() {
            return Mockito.mock(AnalyticsService.class);
        }

        @Bean
        public RateLimiterService rateLimiterService() {
            return Mockito.mock(RateLimiterService.class);
        }
    }

    @Test
    void testShortenUrlSuccess() throws Exception {
        when(rateLimiterService.isAllowed(any())).thenReturn(true);
        when(shortenerService.shortenUrl(any(), any(), any())).thenReturn("abc123");

        String json = """
            {
              "longUrl": "https://example.com/page",
              "customAlias": null,
              "expiresAt": null
            }
        """;

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").value("http://localhost:8080/abc123"));
    }
}
