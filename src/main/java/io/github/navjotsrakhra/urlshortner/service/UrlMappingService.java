package io.github.navjotsrakhra.urlshortner.service;

import io.github.navjotsrakhra.urlshortner.data.model.UrlMapping;
import io.github.navjotsrakhra.urlshortner.data.model.dto.UrlMappingDTO;
import io.github.navjotsrakhra.urlshortner.repository.UrlMappingRepository;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlMappingService {
    private final UrlMappingRepository urlMappingRepository;
    private final Validator validator;

    public UrlMappingService(UrlMappingRepository urlMappingRepository, Validator validator) {
        this.urlMappingRepository = urlMappingRepository;
        this.validator = validator;
    }

    public ResponseEntity<UrlMappingDTO> getUrlByKey(String key) {
        var urlMapping = urlMappingRepository.findByKey(key);

        return urlMapping.map(
                mapping ->
                        ResponseEntity.ok(
                                urlMapping.get().toUrlMappingDTO()
                        )
        ).orElseGet(
                () -> ResponseEntity.notFound().build()
        );

    }

    public ResponseEntity<Void> deleteUrlByKey(String key) {
        var urlMapping = urlMappingRepository.findByKey(key);

        if (urlMapping.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        urlMappingRepository.delete(urlMapping.get());

        return ResponseEntity.ok().build();
    }


    public ResponseEntity<Void> updateUrlByKeyIfOwner(String id, @Valid UrlMapping urlMapping, String name) {
        var urlMappingOptional = urlMappingRepository.findByKey(id);

        if (urlMappingOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var urlMappingFromDb = urlMappingOptional.get();

        if (!urlMappingFromDb.getOwner().equals(name)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        urlMappingFromDb.setLongUrl(urlMapping.getLongUrl());
        urlMappingFromDb.setActive(urlMapping.getActive());
        urlMappingFromDb.setPermanent(urlMapping.getPermanent());
        urlMappingFromDb.setExpiresAt(urlMapping.getExpiresAt());

        urlMappingRepository.save(urlMappingFromDb);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<UrlMappingDTO> createUrlMapping(UrlMapping urlMapping, String name) {
        urlMapping.setOwner(name);

        // Validate the urlMapping object.
        try {
            var violations = validator.validate(urlMapping);
            if (!violations.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        urlMappingRepository.save(urlMapping);

        return ResponseEntity.ok(urlMapping.toUrlMappingDTO());
    }

    public ResponseEntity<List<UrlMappingDTO>> getAllUrlsOwnedByCurrentUser(String name) {
        var urlMappings = urlMappingRepository.findAllByOwner(name);

        return ResponseEntity.ok(
                urlMappings.stream()
                        .map(
                                UrlMapping::toUrlMappingDTO
                        )
                        .toList()
        );
    }
}
