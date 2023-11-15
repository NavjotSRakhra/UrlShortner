package io.github.navjotsrakhra.urlshortner.controller;

import io.github.navjotsrakhra.urlshortner.service.ShortenedUrlRedirectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ShortenedUrlRedirectionController {
    private final ShortenedUrlRedirectionService shortenedUrlRedirectionService;

    public ShortenedUrlRedirectionController(ShortenedUrlRedirectionService shortenedUrlRedirectionService) {
        this.shortenedUrlRedirectionService = shortenedUrlRedirectionService;
    }


    @GetMapping("/{key}")
    public ResponseEntity<Void> redirect(@PathVariable String key) {
        return shortenedUrlRedirectionService.redirect(key);
    }

    @GetMapping
    public ResponseEntity<Void> redirect() {
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .header("Location", "/static/index.html")
                .build();
    }
}
