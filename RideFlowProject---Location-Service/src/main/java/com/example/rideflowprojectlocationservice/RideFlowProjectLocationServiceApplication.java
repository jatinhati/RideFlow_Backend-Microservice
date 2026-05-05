package com.example.rideflowprojectlocationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RideFlowProjectLocationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideFlowProjectLocationServiceApplication.class, args);
    }

}
