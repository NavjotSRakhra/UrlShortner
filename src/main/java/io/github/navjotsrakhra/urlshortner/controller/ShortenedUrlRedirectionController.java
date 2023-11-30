package io.github.navjotsrakhra.urlshortner.controller;

import io.github.navjotsrakhra.urlshortner.service.ShortenedUrlRedirectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class ShortenedUrlRedirectionController {
    private final ShortenedUrlRedirectionService shortenedUrlRedirectionService;

    public ShortenedUrlRedirectionController(ShortenedUrlRedirectionService shortenedUrlRedirectionService) {
        this.shortenedUrlRedirectionService = shortenedUrlRedirectionService;
    }


    @GetMapping("/{key}")
    public ResponseEntity<Void> redirect(@PathVariable String key, @RequestHeader Map<String, String> headers) {
        return shortenedUrlRedirectionService.redirect(key, headers.get("user-agent"));
    }

    @GetMapping
    public ResponseEntity<Void> redirect() {
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .header("Location", "/static/index.html")
                .build();
    }
}
