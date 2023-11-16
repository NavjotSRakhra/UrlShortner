package io.github.navjotsrakhra.urlshortner.repository;

import io.github.navjotsrakhra.urlshortner.data.model.UrlMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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
            AND
            u.active = TRUE
            """)
    Optional<UrlMapping> findActiveByKey(String key);

    Optional<UrlMapping> findByKeyAndOwner(String key, String owner);

    @Query("""
            SELECT u
            FROM UrlMapping u
            WHERE
            u.owner = :owner
            AND (
                u.permanent = TRUE
                OR
                u.expiresAt > CURRENT_TIMESTAMP
            )
            """)
    Page<UrlMapping> findAllNotExpiredByOwner(String owner, Pageable pageable);
}
