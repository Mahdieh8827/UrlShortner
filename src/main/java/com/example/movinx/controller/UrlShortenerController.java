package com.example.movinx.controller;

import com.example.movinx.dto.UrlDto;
import com.example.movinx.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortenerController {

    UrlShortenerService urlShortenerService;
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/encode")
    public ResponseEntity<String> encode(@RequestBody UrlDto urlDto) {
        return new ResponseEntity<>(urlShortenerService.encodeToShortenerUrl(urlDto.getLongUrl()), HttpStatus.OK);
    }

    @GetMapping("/decode")
    public ResponseEntity<String> decode(@RequestParam String shortUrl) throws Exception {
        return new ResponseEntity<>(urlShortenerService.decodeToOriginalUrl(shortUrl), HttpStatus.OK);
    }
}
