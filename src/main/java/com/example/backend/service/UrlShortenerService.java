package com.example.backend.service;

import com.example.backend.model.ClickEvent;
import com.example.backend.model.UrlMapping;
import com.example.backend.repository.ClickEventRepository;
import com.example.backend.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository urlMappingRepo;

    @Autowired
    private ClickEventRepository clickEventRepo;

    public Map<String, Object> createShortUrl(String url, Integer validityMinutes, String customShortcode) {
        String shortcode = (customShortcode != null && !customShortcode.isEmpty())
                ? customShortcode : UUID.randomUUID().toString().substring(0, 6);

        if (urlMappingRepo.findByShortcode(shortcode).isPresent())
            throw new IllegalArgumentException("Shortcode already in use");

        UrlMapping mapping = new UrlMapping();
        mapping.setShortcode(shortcode);
        mapping.setOriginalUrl(url);
        mapping.setCreationTime(LocalDateTime.now());
        mapping.setExpiryTime(LocalDateTime.now().plusMinutes(validityMinutes != null ? validityMinutes : 30));
        urlMappingRepo.save(mapping);

        Map<String, Object> response = new HashMap<>();
        response.put("shortLink", "http://localhost:8080/" + shortcode);
        response.put("expiry", mapping.getExpiryTime().toString());
        return response;
    }

    public String redirectToOriginal(String shortcode, String referrer, String geo) {
        UrlMapping mapping = urlMappingRepo.findByShortcode(shortcode)
                .orElseThrow(() -> new RuntimeException("Shortcode not found"));

        if (mapping.getExpiryTime().isBefore(LocalDateTime.now()))
            throw new IllegalStateException("Short link expired");

        mapping.setClickCount(mapping.getClickCount() + 1);
        urlMappingRepo.save(mapping);

        ClickEvent event = new ClickEvent();
        event.setShortcode(shortcode);
        event.setTimestamp(LocalDateTime.now());
        event.setReferrer(referrer);
        event.setGeoLocation(geo);
        clickEventRepo.save(event);

        return mapping.getOriginalUrl();
    }

    public Map<String, Object> getStatistics(String shortcode) {
        UrlMapping mapping = urlMappingRepo.findByShortcode(shortcode)
                .orElseThrow(() -> new RuntimeException("Shortcode not found"));

        List<ClickEvent> events = clickEventRepo.findByShortcode(shortcode);

        Map<String, Object> stats = new HashMap<>();
        stats.put("clickCount", mapping.getClickCount());
        stats.put("originalUrl", mapping.getOriginalUrl());
        stats.put("creationDate", mapping.getCreationTime());
        stats.put("expiryDate", mapping.getExpiryTime());
        stats.put("clickEvents", events);

        return stats;
    }
}
