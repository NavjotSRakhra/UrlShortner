package io.github.navjotsrakhra.urlshortner.service;


import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class KeepActiveScheduledService {
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
    public void keepActive() {
        var url = System.getenv("base_url");

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.DEFAULT).build()) {
            HttpGet get = new HttpGet(url);
            var response = httpClient.execute(get);
            System.out.println(new String(response.getEntity().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}