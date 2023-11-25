package io.github.navjotsrakhra.urlshortner.service;

import io.github.navjotsrakhra.urlshortner.data.model.UrlMapping;
import io.github.navjotsrakhra.urlshortner.repository.TrafficRepository;
import io.github.navjotsrakhra.urlshortner.repository.UrlMappingRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class ShortenedUrlRedirectionService {
    private final UrlMappingRepository urlMappingRepository;
    private final TrafficRepository trafficRepository;

    public ShortenedUrlRedirectionService(UrlMappingRepository urlMappingRepository, TrafficRepository trafficRepository) {
        this.urlMappingRepository = urlMappingRepository;
        this.trafficRepository = trafficRepository;
    }

    public ResponseEntity<Void> redirect(String key) {
        var urlMapping = urlMappingRepository.findActiveByKey(key);

        urlMapping.ifPresent(this::updateCount);

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

    @Transactional
    public void updateCount(UrlMapping urlMapping) {
        var traffic = urlMapping.getTraffic();
        traffic.visited("Unknown");
        trafficRepository.save(traffic);
    }
}
