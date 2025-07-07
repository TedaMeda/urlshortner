package com.gj4.urlshortner.config;

import com.gj4.urlshortner.model.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoTTLConfig implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) {
        Index index = new Index()
                .on("expiresAt", Sort.Direction.ASC)
                .expire(0); // expire at the exact time

        mongoTemplate
                .indexOps(UrlMapping.class)
                .createIndex(index);
    }
}
