package com.example.search.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class SearchService {

    @Autowired
    private RestTemplate restTemplate;

    // Asynchronous call to universities service with Hystrix fallback
    @HystrixCommand(fallbackMethod = "fallbackUniversitiesService")
    public CompletableFuture<Map<String, Object>> callUniversitiesServiceAsync() {
        return CompletableFuture.supplyAsync(() -> restTemplate.getForObject("http://universities-service/api/universities", Map.class));
    }

    // Asynchronous call to details service with Hystrix fallback
    @HystrixCommand(fallbackMethod = "fallbackDetailsService")
    public CompletableFuture<Map<String, Object>> callDetailsServiceAsync() {
        return CompletableFuture.supplyAsync(() -> restTemplate.getForObject("http://details/port", Map.class));
    }

    public Map<String, Object> getSearchResults() {
        try {
            // Perform asynchronous calls
            CompletableFuture<Map<String, Object>> universitiesFuture = callUniversitiesServiceAsync();
            CompletableFuture<Map<String, Object>> detailsFuture = callDetailsServiceAsync();

            // Wait for both responses
            CompletableFuture.allOf(universitiesFuture, detailsFuture).join();

            // Collect results
            Map<String, Object> universities = universitiesFuture.get();
            Map<String, Object> details = detailsFuture.get();

            // Create the response
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("timestamp", System.currentTimeMillis());

            // Dynamically create a data map
            Map<String, Object> data = new HashMap<>();
            data.put("universities", universities);
            data.put("details", details);

            response.put("data", data);

            return response;

        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions gracefully
            throw new RuntimeException("Error fetching search results", e);
        }
    }

    // Fallback method for the universities service
    public Map<String, Object> fallbackUniversitiesService() {
        Map<String, Object> fallbackResponse = new HashMap<>();
        fallbackResponse.put("error", "Universities service is unavailable");
        return fallbackResponse;
    }

    // Fallback method for the details service
    public Map<String, Object> fallbackDetailsService() {
        Map<String, Object> fallbackResponse = new HashMap<>();
        fallbackResponse.put("error", "Details service is unavailable");
        return fallbackResponse;
    }
}
