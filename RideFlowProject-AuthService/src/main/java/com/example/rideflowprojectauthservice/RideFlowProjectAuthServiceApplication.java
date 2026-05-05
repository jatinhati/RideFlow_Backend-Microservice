package com.example.rideflowprojectauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.example.rideflowprojectentityservice.models")
public class RideFlowProjectAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideFlowProjectAuthServiceApplication.class, args);
    }

}
