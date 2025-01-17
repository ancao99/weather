//package com.example.search;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.context.annotation.ComponentScan;
//
//@SpringBootApplication
//@EnableEurekaClient
//@EnableCircuitBreaker
//public class SearchApplication {
//
//    public static void main(String[] args) {
//
//        SpringApplication.run(SearchApplication.class, args);
//    }
//
//}
package com.example.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

//zuul vs spring cloud gateway
//ribbon
@EnableEurekaClient
@SpringBootApplication
public class SearchApplication {

    public static void main(String[] args) {

        SpringApplication.run(SearchApplication.class, args);
    }

    //http://search-service/weather/search
    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClientBuilder() {

        return WebClient.builder();
    }

}
