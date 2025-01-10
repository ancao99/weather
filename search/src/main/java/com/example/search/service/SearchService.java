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

    // Asynchronous call to book service with Hystrix fallback
    @HystrixCommand(fallbackMethod = "fallbackBookService")
    public CompletableFuture<Map<String, Object>> callBookServiceAsync() {
        return CompletableFuture.supplyAsync(() -> restTemplate.getForObject("http://book-service/api/books", Map.class));
    }

    // Asynchronous call to details service with Hystrix fallback
    @HystrixCommand(fallbackMethod = "fallbackDetailsService")
    public CompletableFuture<Map<String, Object>> callDetailsServiceAsync() {
        return CompletableFuture.supplyAsync(() -> restTemplate.getForObject("http://details/port", Map.class));
    }

    public Map<String, Object> getSearchResults() {
        try {
            // Perform asynchronous calls
            CompletableFuture<Map<String, Object>> bookFuture = callBookServiceAsync();
            CompletableFuture<Map<String, Object>> detailsFuture = callDetailsServiceAsync();

            // Wait for both responses
            CompletableFuture.allOf(bookFuture, detailsFuture).join();

            // Collect results
            Map<String, Object> books = bookFuture.get();
            Map<String, Object> details = detailsFuture.get();

            // Create the response
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("timestamp", System.currentTimeMillis());

            // Dynamically create a data map
            Map<String, Object> data = new HashMap<>();
            data.put("books", books);
            data.put("details", details);

            response.put("data", data);

            return response;

        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions gracefully
            throw new RuntimeException("Error fetching search results", e);
        }
    }

    // Fallback method for the book service
    public Map<String, Object> fallbackBookService() {
        Map<String, Object> fallbackResponse = new HashMap<>();
        fallbackResponse.put("error", "Book service is unavailable");
        return fallbackResponse;
    }

    // Fallback method for the details service
    public Map<String, Object> fallbackDetailsService() {
        Map<String, Object> fallbackResponse = new HashMap<>();
        fallbackResponse.put("error", "Details service is unavailable");
        return fallbackResponse;
    }
}
