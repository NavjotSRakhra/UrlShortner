package io.github.navjotsrakhra.urlshortner.data.model;

import io.github.navjotsrakhra.urlshortner.data.model.dto.UrlMappingDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;
import java.util.StringJoiner;
import java.util.UUID;

@Entity
public class UrlMapping {
    @GenericGenerator(name = "uuid", type = UuidGenerator.class)
    @GeneratedValue(strategy = GenerationType.UUID)
    private @Id UUID id;
    @Column(columnDefinition = "TEXT")
    private @NotEmpty @URL String longUrl;
    private @NotEmpty String key;
    private @NotEmpty String owner;
    private @NotNull Boolean active;
    private @NotNull Boolean permanent;
    private @NotNull Instant expiresAt;
    private @NotNull Instant createdAt; // Not to be intialized by the user
    private @NotNull Long accessCount; // Not to be intialized by the user

    public UrlMapping() {
    }

    public UrlMapping(String longUrl, String key, String owner, Boolean active, Boolean permanent, Instant expiresAt) {
        this(null, longUrl, key, owner, active, permanent, expiresAt, Instant.now(), 0L);
    }

    public UrlMapping(String longUrl, String key, String owner, Boolean active, Boolean permanent, Instant expiresAt, Instant createdAt, Long accessCount) {
        this(null, longUrl, key, owner, active, permanent, expiresAt, createdAt, accessCount);
    }

    public UrlMapping(String longUrl, String key, Boolean active, Boolean permanent, Instant expiresAt, Instant createdAt, Long accessCount) {
        this(null, longUrl, key, null, active, permanent, expiresAt, createdAt, accessCount);
    }

    public UrlMapping(UUID id, String longUrl, String key, String owner, Boolean active, Boolean permanent, Instant expiresAt, Instant createdAt, Long accessCount) {
        this.id = id;
        this.longUrl = longUrl;
        this.key = key;
        this.owner = owner;
        this.active = active;
        this.permanent = permanent;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.accessCount = accessCount;
    }


    public UrlMappingDTO toUrlMappingDTO() {
        return new UrlMappingDTO(id, longUrl, key, active, permanent, expiresAt, createdAt, accessCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlMapping that)) return false;

        if (!getId().equals(that.getId())) return false;
        if (!getLongUrl().equals(that.getLongUrl())) return false;
        if (!getKey().equals(that.getKey())) return false;
        if (!getOwner().equals(that.getOwner())) return false;
        if (!getActive().equals(that.getActive())) return false;
        if (!getPermanent().equals(that.getPermanent())) return false;
        if (!getExpiresAt().equals(that.getExpiresAt())) return false;
        if (!getCreatedAt().equals(that.getCreatedAt())) return false;
        return getAccessCount().equals(that.getAccessCount());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getLongUrl().hashCode();
        result = 31 * result + getKey().hashCode();
        result = 31 * result + getOwner().hashCode();
        result = 31 * result + getActive().hashCode();
        result = 31 * result + getPermanent().hashCode();
        result = 31 * result + getExpiresAt().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        result = 31 * result + getAccessCount().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UrlMapping.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("key='" + key + "'")
                .add("longUrl='" + longUrl + "'")
                .add("owner='" + owner + "'")
                .add("isActive=" + active)
                .add("isPermanent=" + permanent)
                .add("expiresAt=" + expiresAt)
                .add("createdAt=" + createdAt)
                .add("accessCount=" + accessCount)
                .toString();
    }
    // Below are the getters and setters for the UrlMapping class.

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getPermanent() {
        return permanent;
    }

    public void setPermanent(Boolean permanent) {
        this.permanent = permanent;
    }

    public @NotNull Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(@NotNull Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public @NotNull Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Long accessCount) {
        this.accessCount = accessCount;
    }
}
