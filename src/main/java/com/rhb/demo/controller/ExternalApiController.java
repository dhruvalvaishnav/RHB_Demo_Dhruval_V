package com.rhb.demo.controller;

import com.rhb.demo.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/external")
public class ExternalApiController {

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping("/testGoogle")
    public ResponseEntity<Map<String, String>> testGoogle() {
        String result = externalApiService.callExternalApi("https://www.google.com");
        Map<String, String> response = new HashMap<>();
        response.put("result", result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/call")
    public ResponseEntity<Map<String, String>> callApi(@RequestBody Map<String, String> request) {
        String url = request.get("url");
        String result = externalApiService.callExternalApi(url);
        Map<String, String> response = new HashMap<>();
        response.put("result", result);
        return ResponseEntity.ok(response);
    }
}

