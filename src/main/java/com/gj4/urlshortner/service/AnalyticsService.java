package com.gj4.urlshortner.service;

import com.gj4.urlshortner.model.ClickLog;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AnalyticsService {
    void logClick(String shortCode, HttpServletRequest request);
    List<ClickLog> getAnalytics(String shortCode);
}
