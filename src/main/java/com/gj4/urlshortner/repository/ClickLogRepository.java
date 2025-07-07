package com.gj4.urlshortner.repository;

import com.gj4.urlshortner.model.ClickLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClickLogRepository extends MongoRepository<ClickLog, String> {
    List<ClickLog> findAllByShortCode(String shortCode);
}
