package io.github.navjotsrakhra.urlshortner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ShortenedUrlRedirectionController {
    @GetMapping("/{key}")
    public ResponseEntity<Void> redirect(@PathVariable String key) {
        return ResponseEntity.ok().build();
    }
}
