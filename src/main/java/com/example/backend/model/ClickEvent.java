package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortcode;
    private LocalDateTime timestamp;
    private String referrer;
    private String geoLocation;

    // Getters and Setters
}
