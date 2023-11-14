package io.github.navjotsrakhra.urlshortner.data.model.dto;

import io.github.navjotsrakhra.urlshortner.data.model.UrlMapping;

import java.time.Instant;
import java.util.UUID;

public record UrlMappingDTO(UUID id, String longUrl, String key, Boolean active, Boolean permanent, Instant expiresAt,
                            Instant createdAt, Long accessCount) {
    public UrlMapping toUrlMapping(String owner) {
        return new UrlMapping(longUrl, key, owner, active, permanent, expiresAt, createdAt, accessCount);
    }
}
