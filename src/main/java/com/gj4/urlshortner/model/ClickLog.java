package com.gj4.urlshortner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "click_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClickLog {
    @Id
    private String id;
    private String shortCode;
    private Instant timestamp;
    private String ip;
    private String userAgent;
}

