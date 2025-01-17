package com.example.search;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.search.model.GeneralResponse;

@Service
public class SearchService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackResponse")
    public GeneralResponse fetchMergedData() throws ExecutionException, InterruptedException {
        CompletableFuture<String> springOneFuture = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject("http://desktop-um4q1jv:9300/universities", String.class));

        CompletableFuture<String> detailsFuture = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject("http://desktop-um4q1jv:9300/details/port", String.class));

        CompletableFuture<Void> allOf = CompletableFuture.allOf(springOneFuture, detailsFuture);
        allOf.join();

        String springOneData = springOneFuture.get();
        String detailsData = detailsFuture.get();

        String mergedData = "SpringOne Data: " + springOneData + ", Details Data: " + detailsData;

        return new GeneralResponse(200, Instant.now().toString(), mergedData);
    }

    public GeneralResponse fallbackResponse() {
        return new GeneralResponse(500, Instant.now().toString(), "Fallback: Services unavailable");
    }
}
