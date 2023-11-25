package io.github.navjotsrakhra.urlshortner.config;


import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class KeepActiveScheduledConfig {
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
    public void keepActive() {
        var url = System.getenv("base_url");

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.DEFAULT).build()) {
            HttpGet get = new HttpGet(url);
            var response = httpClient.execute(get);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
