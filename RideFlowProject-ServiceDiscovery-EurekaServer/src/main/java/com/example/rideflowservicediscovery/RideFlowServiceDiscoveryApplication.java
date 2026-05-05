package com.example.rideflowservicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RideFlowServiceDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideFlowServiceDiscoveryApplication.class, args);
    }

}
