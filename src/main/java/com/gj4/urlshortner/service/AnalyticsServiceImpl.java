package com.gj4.urlshortner.service;

import com.gj4.urlshortner.model.ClickLog;
import com.gj4.urlshortner.repository.ClickLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
    private final ClickLogRepository clickLogRepo;

    @Override
    public void logClick(String shortCode, HttpServletRequest request) {
        ClickLog log = ClickLog.builder()
                .shortCode(shortCode)
                .ip(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .timestamp(Instant.now())
                .build();

        clickLogRepo.save(log);
    }

    @Override
    public List<ClickLog> getAnalytics(String shortCode) {
        return clickLogRepo.findAllByShortCode(shortCode);
    }
}
