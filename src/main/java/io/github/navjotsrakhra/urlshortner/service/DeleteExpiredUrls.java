package io.github.navjotsrakhra.urlshortner.service;

import org.springframework.scheduling.annotation.Scheduled;

public class DeleteExpiredUrls {
    private final UrlMappingService urlMappingService;

    public DeleteExpiredUrls(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
    public void deleteExpiredUrls() {
        urlMappingService.deleteExpiredUrls();
    }

}
