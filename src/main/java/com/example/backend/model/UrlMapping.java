package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String shortcode;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    private LocalDateTime creationTime;
    private LocalDateTime expiryTime;
    private int clickCount = 0;

    // Getters and Setters
}
