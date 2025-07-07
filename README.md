# 🚀 URL Shortener Microservice

A simple Spring Boot microservice that allows users to create, manage, and track shortened URLs with optional expiry, redirection, and usage statistics.

---

## 📌 Features

- 🔗 Shorten long URLs with optional custom shortcodes.
- ⏳ Set expiry duration for short links.
- 🚀 Automatic redirection to the original URL.
- 📊 Track click counts and detailed access logs (IP, referrer, timestamp).
- 🛡️ Includes logging middleware for request/response monitoring.

---

## ⚙️ Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 / MySQL Database
- Maven
- Lombok (optional)
- Postman (for testing)

---

## 🏗️ Project Structure

src/main/java/com/example/backend/
├── controller/
│ └── UrlShortenerController.java
├── model/
│ ├── UrlMapping.java
│ └── ClickEvent.java
├── repository/
│ ├── UrlMappingRepository.java
│ └── ClickEventRepository.java
├── service/
│ └── UrlShortenerService.java
├── middleware/
│ └── LoggingMiddleware.java
└── UrlShortenerApplication.java



