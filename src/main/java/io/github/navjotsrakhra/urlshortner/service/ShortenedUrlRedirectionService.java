package io.github.navjotsrakhra.urlshortner.service;

import io.github.navjotsrakhra.urlshortner.repository.UrlMappingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class ShortenedUrlRedirectionService {
    private final UrlMappingRepository urlMappingRepository;

    public ShortenedUrlRedirectionService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public ResponseEntity<Void> redirect(String key) {
        var urlMapping = urlMappingRepository.findNotExpiredByKey(key);

        return urlMapping.<ResponseEntity<Void>>map(
                        mapping -> ResponseEntity
                                .status(HttpStatus.SEE_OTHER)
                                .location(
                                        URI.create(
                                                mapping.getLongUrl()
                                        )
                                )
                                .build()
                )
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }
}
