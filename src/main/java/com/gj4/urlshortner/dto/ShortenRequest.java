package com.gj4.urlshortner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to shorten a long URL")
public class ShortenRequest {

    @NotBlank
    @Schema(description = "Original long URL", example = "https://example.com/my-page")
    private String longUrl;

    @Schema(description = "Optional custom short alias", example = "my-alias")
    private String customAlias;

    @Schema(description = "Expiration time in UTC ISO format", example = "2025-07-01T12:00:00Z")
    private Instant expiresAt;
}

