package io.github.navjotsrakhra.urlshortner.repository;

import io.github.navjotsrakhra.urlshortner.data.model.UrlMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlMappingRepository extends CrudRepository<UrlMapping, UUID> {
    @Query("""
            SELECT u
            FROM UrlMapping u
            WHERE
            u.key = :key
            AND (
                u.permanent = TRUE
                OR
                u.expiresAt > CURRENT_TIMESTAMP
            )
            """)
    Optional<UrlMapping> findNotExpiredByKey(String key);

    Optional<UrlMapping> findByKey(String key);

    List<UrlMapping> findAllByOwner(String owner);
}
