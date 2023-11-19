package io.github.navjotsrakhra.urlshortner.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

public class KeepActiveScheduledService {
    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void keepActive() {
        String url = System.getenv("base_url");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(url, String.class);
    }
}
