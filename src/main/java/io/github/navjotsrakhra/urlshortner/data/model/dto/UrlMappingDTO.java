package io.github.navjotsrakhra.urlshortner.data.model.dto;

import io.github.navjotsrakhra.urlshortner.data.model.UrlMapping;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;
import java.util.UUID;

public record UrlMappingDTO(UUID id, @NotNull @URL String longUrl, @NotNull String key, Boolean active,
                            Boolean permanent,
                            Instant expiresAt,
                            Instant createdAt, Long accessCount) {
    public UrlMappingDTO {
        if (active == null) active = true;
        if (permanent == null) permanent = false;
        if (expiresAt == null) expiresAt = Instant.now();
    }

    public UrlMapping toUrlMapping() {
        return new UrlMapping(longUrl, key, active, permanent, expiresAt, createdAt, accessCount);
    }
}
