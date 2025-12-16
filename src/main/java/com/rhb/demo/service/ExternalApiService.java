package com.rhb.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;

    public ExternalApiService() {
        this.restTemplate = new RestTemplate();
    }

    public String callExternalApi(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            log.info("Calling external API: {}", url);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            int statusCode = response.getStatusCode().value();
            String responseBody = response.getBody();
            int contentLength = responseBody != null ? responseBody.length() : 0;

            log.info("External API response - Status: {}, Length: {} bytes", statusCode, contentLength);

            return String.format("External API called successfully. Status: %d, Content Length: %d bytes", statusCode, contentLength);
        } catch (Exception e) {
            log.error("Error calling external API: {}", e.getMessage(), e);
            return "Error calling external API: " + e.getMessage();
        }
    }
}

