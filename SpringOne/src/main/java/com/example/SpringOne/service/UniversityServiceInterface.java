package com.example.SpringOne.service;

import com.example.SpringOne.pojo.University;

import java.util.List;
import java.util.Map;

public interface UniversityServiceInterface {

    List<University> getUniversitiesByCountry(String country);

    Map<String, List<University>> getUniversitiesForMultipleCountries(List<String> countries);
}
