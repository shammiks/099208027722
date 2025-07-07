package com.example.backend.controller;

import com.example.backend.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/shorturls")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService service;

    @PostMapping
    public ResponseEntity<?> createShort(@RequestBody Map<String, Object> body) {
        String url = (String) body.get("url");
        Integer validity = (Integer) body.getOrDefault("validity", 30);
        String shortcode = (String) body.get("shortcode");

        Map<String, Object> response = service.createShortUrl(url, validity, shortcode);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortcode}")
    public ResponseEntity<?> redirect(@PathVariable String shortcode, HttpServletRequest req) {
        String referrer = req.getHeader("referer");
        String geo = req.getRemoteAddr();
        String original = service.redirectToOriginal(shortcode, referrer, geo);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(original));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/{shortcode}/stats")
    public ResponseEntity<?> stats(@PathVariable String shortcode) {
        return ResponseEntity.ok(service.getStatistics(shortcode));
    }
}
