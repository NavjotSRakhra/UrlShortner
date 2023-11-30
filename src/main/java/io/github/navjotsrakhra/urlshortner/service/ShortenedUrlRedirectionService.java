package io.github.navjotsrakhra.urlshortner.service;

import com.blueconic.browscap.*;
import io.github.navjotsrakhra.urlshortner.data.model.Redirect;
import io.github.navjotsrakhra.urlshortner.data.model.UrlMapping;
import io.github.navjotsrakhra.urlshortner.repository.UrlMappingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Arrays;

@Service
public class ShortenedUrlRedirectionService {
    private final UrlMappingRepository urlMappingRepository;

    private final UserAgentParser parser;

    public ShortenedUrlRedirectionService(UrlMappingRepository urlMappingRepository) throws IOException, ParseException {
        this.urlMappingRepository = urlMappingRepository;
        parser = new UserAgentService().loadParser(Arrays.asList(BrowsCapField.PLATFORM, BrowsCapField.PLATFORM_VERSION));
    }

    public ResponseEntity<Void> redirect(String key, String userAgent) {
        var urlMapping = urlMappingRepository.findActiveByKey(key);

        urlMapping.ifPresent(a -> updateCount(a, userAgent));

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

    public void updateCount(UrlMapping urlMapping, String userAgent) {
        Capabilities capabilities = parser.parse(userAgent);

        var traffic = urlMapping.getTraffic();
        traffic.visited();
        var redirect = new Redirect(Instant.now(), capabilities.getPlatform() + " " + capabilities.getPlatformVersion(), traffic);
        traffic.getRedirects().add(redirect);

        urlMappingRepository.save(urlMapping);
    }
}
