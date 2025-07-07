package com.gj4.urlshortner.controller;

import com.gj4.urlshortner.dto.ShortenRequest;
import com.gj4.urlshortner.model.ClickLog;
import com.gj4.urlshortner.service.AnalyticsService;
import com.gj4.urlshortner.service.RateLimiterService;
import com.gj4.urlshortner.service.RedirectService;
import com.gj4.urlshortner.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Tag(name = "URL Shortener", description = "Endpoints to create and resolve short URLs")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UrlController {
    private final UrlShortenerService shortenerService;
    private final RedirectService redirectService;
    private final AnalyticsService analyticsService;
    private final RateLimiterService rateLimiterService;

    @Operation(summary = "Shorten a long URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Short URL created"),
            @ApiResponse(responseCode = "429", description = "Rate limit exceeded")
    })
    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shorten(@RequestBody ShortenRequest request, HttpServletRequest servletRequest) {
        String clientIp = servletRequest.getRemoteAddr();

        if(!rateLimiterService.isAllowed(clientIp)){
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        String shortCode = shortenerService.shortenUrl(
                request.getLongUrl(),
                request.getCustomAlias(),
                request.getExpiresAt()
        );
        return ResponseEntity.ok(Map.of("shortUrl", "http://localhost:8080/" + shortCode));
    }

    @Operation(summary = "Redirect from short URL")
    @ApiResponse(responseCode = "302", description = "Redirection to original URL")
    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode,
                                      HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();

        if (!rateLimiterService.isAllowed(clientIp)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        String target = redirectService.resolveUrl(
                shortCode,
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
        );
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(target)).build();
    }

    @Operation(summary = "Get analytics for a short URL")
    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<List<ClickLog>> analytics(@PathVariable String shortCode) {
        return ResponseEntity.ok(analyticsService.getAnalytics(shortCode));
    }
}
