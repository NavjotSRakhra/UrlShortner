package io.github.navjotsrakhra.urlshortner.controller;

import io.github.navjotsrakhra.urlshortner.data.model.dto.UrlMappingDTO;
import io.github.navjotsrakhra.urlshortner.service.UrlMappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/manage/url")
public class UrlMappingController {
    private final UrlMappingService urlMappingService;

    public UrlMappingController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @GetMapping("/{key}")
    public ResponseEntity<UrlMappingDTO> getUrlByKey(@PathVariable String key) {
        return urlMappingService.getUrlByKey(key);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteUrlById(@PathVariable String key) {
        return urlMappingService.deleteUrlByKey(key);
    }

    @PatchMapping("/{key}")
    public ResponseEntity<Void> updateUrlById(@PathVariable String key, @RequestBody UrlMappingDTO urlMappingDTO, Principal principal) {
        return urlMappingService.updateUrlByKeyIfOwner(key, urlMappingDTO.toUrlMapping(), principal.getName());
    }

    @PostMapping("/create")
    public ResponseEntity<UrlMappingDTO> createUrl(@RequestBody UrlMappingDTO urlMappingDTO, Principal principal) {
        return urlMappingService.createUrlMapping(urlMappingDTO.toUrlMapping(), principal.getName());
    }

    @GetMapping
    public ResponseEntity<List<UrlMappingDTO>> getAllUrlsOwnedByCurrentUser(Principal principal) {
        return urlMappingService.getAllUrlsOwnedByCurrentUser(principal.getName());
    }
}
