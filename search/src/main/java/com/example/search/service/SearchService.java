//package com.example.search.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//@Service
//public class SearchService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    // Asynchronous call to SpringOne service
//    public CompletableFuture<Map<String, Object>> callSpringOneServiceAsync() {
//        return CompletableFuture.supplyAsync(() ->
//                restTemplate.getForObject("http://SpringOne/api/data", Map.class));
//    }
//
//    // Asynchronous call to Details service
//    public CompletableFuture<Map<String, Object>> callDetailsServiceAsync() {
//        return CompletableFuture.supplyAsync(() ->
//                restTemplate.getForObject("http://details/port", Map.class));
//    }
//    public Map<String, Object> getAggregatedResults() {
//        try {
//            CompletableFuture<Map<String, Object>> detailsFuture = callDetailsServiceAsync();
//
//            CompletableFuture.allOf( detailsFuture).join();
//
//            //Map<String, Object> springOneResponse = springOneFuture.get();
//            Map<String, Object> detailsResponse = detailsFuture.get();
//
//            // Create general response
//            Map<String, Object> response = new HashMap<>();
//            response.put("code", 200);
//            response.put("timestamp", System.currentTimeMillis());
//
//            Map<String, Object> data = new HashMap<>();
//            //data.put("springOneData", springOneResponse);
//            data.put("detailsData", detailsResponse);
//
//            response.put("data", data);
//
//            return response;
//
//        } catch (InterruptedException | ExecutionException e) {
//            // Handle exceptions gracefully
//            throw new RuntimeException("Error fetching aggregated results", e);
//        }
//    }
//
//    // Aggregates results from SpringOne and Details services
////    public Map<String, Object> getAggregatedResults() {
////        try {
////            // Perform asynchronous calls
////            CompletableFuture<Map<String, Object>> springOneFuture = callSpringOneServiceAsync();
////            CompletableFuture<Map<String, Object>> detailsFuture = callDetailsServiceAsync();
////
////            // Wait for both calls to complete
////            CompletableFuture.allOf(springOneFuture, detailsFuture).join();
////
////            // Collect results
////            Map<String, Object> springOneResponse = springOneFuture.get();
////            Map<String, Object> detailsResponse = detailsFuture.get();
////
////            // Create general response
////            Map<String, Object> response = new HashMap<>();
////            response.put("code", 200);
////            response.put("timestamp", System.currentTimeMillis());
////
////            Map<String, Object> data = new HashMap<>();
////            data.put("springOneData", springOneResponse);
////            data.put("detailsData", detailsResponse);
////
////            response.put("data", data);
////
////            return response;
////
////        } catch (InterruptedException | ExecutionException e) {
////            // Handle exceptions gracefully
////            throw new RuntimeException("Error fetching aggregated results", e);
////        }
////    }
//
//    // Method to provide search results
//    public Map<String, Object> getSearchResults() {
//        // Call the method to get aggregated results
//        return getAggregatedResults();
//    }
//
//    // Method to get universities by country
//    public String getUniversitiesByCountry(String country) {
//        // Replace the URL with the actual endpoint of the university API
//        String url = "http://desktop-um4q1jv:9300/universities/search?country=" + country;
//        return restTemplate.getForObject(url, String.class);
//    }
//}
