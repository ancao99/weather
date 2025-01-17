package com.example.SpringOne.service;

import com.example.SpringOne.pojo.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class UniversityService {

    private static final String API_BASE_URL = "http://universities.hipolabs.com/search";

    @Autowired
    private RestTemplate restTemplate;

    // Fetch universities by a single country
    public List<University> getUniversitiesByCountry(String country) {
        String url = API_BASE_URL + "?country=" + country;
        try {
            University[] response = restTemplate.getForObject(url, University[].class);
            List<University> universities = new ArrayList<>();
            if (response != null) {
                for (University university : response) {
                    universities.add(university);
                }
            }
            return universities;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch data for country: " + country, e);
        }
    }

    // Fetch universities for multiple countries using multithreading
    public Map<String, List<University>> getUniversitiesForMultipleCountries(List<String> countries) {
        List<CompletableFuture<List<University>>> futures = new ArrayList<>();

        for (String country : countries) {
            futures.add(CompletableFuture.supplyAsync(() -> getUniversitiesByCountry(country)));
        }

        Map<String, List<University>> allResults = new HashMap<>();
        for (int i = 0; i < futures.size(); i++) {
            String country = countries.get(i);
            CompletableFuture<List<University>> future = futures.get(i);
            try {
                allResults.put(country, future.get());
            } catch (Exception e) {
                throw new RuntimeException("Error while fetching data for one or more countries", e);
            }
        }
        return allResults;
    }

    // Fetch all universities from the external API
    public List<University> getAllUniversities() {
        String url = API_BASE_URL;
        try {
            University[] response = restTemplate.getForObject(url, University[].class);
            if (response != null) {
                List<University> universities = new ArrayList<>();
                for (University university : response) {
                    universities.add(university);
                }
                return universities;
            }
            return new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all universities", e);
        }
    }
}
