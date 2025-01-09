package com.example.search.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class SearchService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackSearch")
    public CompletableFuture<GeneralResponse> search() {
        return CompletableFuture.supplyAsync(() -> {
            String bookData = restTemplate.getForObject("http://book-service/books", String.class);
            String authorData = restTemplate.getForObject("http://author-service/authors", String.class);
            String detailsData = restTemplate.getForObject("http://details-service/details/port", String.class);

            // Combine results
            Map<String, String> result = new HashMap<>();
            result.put("book", bookData);
            result.put("author", authorData);
            result.put("details", detailsData);

            return new GeneralResponse(200, Instant.now(), result);
        });
    }

    public GeneralResponse fallbackSearch() {
        return new GeneralResponse(500, Instant.now(), "Fallback: Services unavailable");
    }
}
