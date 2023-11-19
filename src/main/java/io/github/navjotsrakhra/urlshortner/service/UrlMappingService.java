package io.github.navjotsrakhra.urlshortner.service;

import io.github.navjotsrakhra.urlshortner.data.model.UrlMapping;
import io.github.navjotsrakhra.urlshortner.data.model.dto.UrlMappingDTO;
import io.github.navjotsrakhra.urlshortner.repository.UrlMappingRepository;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UrlMappingService {
    private final UrlMappingRepository urlMappingRepository;
    private final Validator validator;
    private final Logger log = LoggerFactory.getLogger(UrlMappingService.class);

    public UrlMappingService(UrlMappingRepository urlMappingRepository, Validator validator) {
        this.urlMappingRepository = urlMappingRepository;
        this.validator = validator;
    }

    public ResponseEntity<UrlMappingDTO> getUrlByKey(String key, String owner) {
        var urlMapping = urlMappingRepository.findByKeyAndOwner(key, owner);

        return urlMapping.map(
                mapping ->
                        ResponseEntity.ok(
                                urlMapping.get().toUrlMappingDTO()
                        )
        ).orElseGet(
                () -> ResponseEntity.notFound().build()
        );

    }

    public ResponseEntity<Void> deleteUrlByKey(String key, String owner) {
        var urlMapping = urlMappingRepository.findByKeyAndOwner(key, owner);

        if (urlMapping.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        urlMappingRepository.delete(urlMapping.get());

        return ResponseEntity.ok().build();
    }


    public ResponseEntity<Void> updateUrlByKeyIfOwner(String id, @Valid UrlMapping urlMapping, String owner) {
        var urlMappingOptional = urlMappingRepository.findByKeyAndOwner(id, owner);

        if (urlMappingOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var urlMappingFromDb = urlMappingOptional.get();
        updateDbData(urlMapping, urlMappingFromDb);

        urlMappingRepository.save(urlMappingFromDb);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<UrlMappingDTO> createUrlMapping(UrlMapping urlMapping, String owner) {
        urlMapping = new UrlMapping(urlMapping.getLongUrl(), urlMapping.getKey(), owner, urlMapping.getActive(), urlMapping.getPermanent(), urlMapping.getExpiresAt());

        ResponseEntity<UrlMappingDTO> build = validateUrlMapping(urlMapping);
        if (build != null) return build;

        urlMappingRepository.save(urlMapping);

        return ResponseEntity.ok(urlMapping.toUrlMappingDTO());
    }

    public ResponseEntity<Page<UrlMappingDTO>> getAllUrlsOwnedByCurrentUser(String name, Pageable pageable) {
        var urlMappings = urlMappingRepository.findAllNotExpiredByOwner(name, pageable);

        return ResponseEntity.ok(
                urlMappings
                        .map(
                                UrlMapping::toUrlMappingDTO
                        )
        );
    }

    private ResponseEntity<UrlMappingDTO> validateUrlMapping(UrlMapping urlMapping) {
        // Validate the urlMapping object.
        try {
            var violations = validator.validate(urlMapping);
            if (!violations.isEmpty()) {
                violations.forEach(
                        violation -> log.error(violation.toString())
                );
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    private void updateDbData(UrlMapping urlMapping, UrlMapping urlMappingFromDb) {
        urlMappingFromDb.setLongUrl(urlMapping.getLongUrl());
        urlMappingFromDb.setActive(urlMapping.getActive());
    }

    public void deleteExpiredUrls() {
        urlMappingRepository.deleteUrlMappingByExpiresAtBeforeAndPermanentIsFalse(java.time.Instant.now());
    }
}
