package com.example.SpringOne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringOneApplication {
    public static void main(String[] args) {

        SpringApplication.run(SpringOneApplication.class, args);
    }
}
