package com.gj4.urlshortner.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "url_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlMapping {
    @Id
    private String id;
    private String shortCode;
    private String longUrl;
    private Instant createdAt;
    private Instant expiresAt;
    private Long clickCount;
}
