package com.gj4.urlshortner.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI urlShortenerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Scalable URL Shortener API")
                        .version("1.0")
                        .description("API documentation for shortening, redirecting, and analytics")
                );
    }
}

