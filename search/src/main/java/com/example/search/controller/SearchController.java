//package com.example.search.controller;
//
//import com.example.search.service.SearchService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class SearchController {
//
//    @Autowired
//    private SearchService searchService;
//
//    @GetMapping("/weather/search")
//    public ResponseEntity<?> getSearchResults() {
//        try {
//            return new ResponseEntity<>(searchService.getSearchResults(), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error fetching search results: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/search")
//    public String getUniversitiesByCountry(@RequestParam String country) {
//        return searchService.getUniversitiesByCountry(country);
//    }
//}
