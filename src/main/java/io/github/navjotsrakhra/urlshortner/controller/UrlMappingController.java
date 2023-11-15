package io.github.navjotsrakhra.urlshortner.controller;

import io.github.navjotsrakhra.urlshortner.data.model.dto.UrlMappingDTO;
import io.github.navjotsrakhra.urlshortner.service.UrlMappingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/manage/url")
public class UrlMappingController {
    private final UrlMappingService urlMappingService;

    public UrlMappingController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @GetMapping("/{key}")
    public ResponseEntity<UrlMappingDTO> getUrlByKey(@PathVariable String key, Principal principal) {
        return urlMappingService.getUrlByKey(key, principal.getName());
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteUrlById(@PathVariable String key, Principal principal) {
        return urlMappingService.deleteUrlByKey(key, principal.getName());
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
    public ResponseEntity<Page<UrlMappingDTO>> getAllUrlsOwnedByCurrentUser(Principal principal, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) { //TODO Paginate
        return urlMappingService.getAllUrlsOwnedByCurrentUser(principal.getName(), pageable);
    }
}
