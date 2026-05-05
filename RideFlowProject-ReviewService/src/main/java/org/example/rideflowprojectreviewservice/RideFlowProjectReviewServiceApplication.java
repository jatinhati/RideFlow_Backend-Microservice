package org.example.rideflowprojectreviewservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication 
@EnableJpaAuditing
@EntityScan("org.example.rideflowprojectentity_service.Model")
public class RideFlowProjectReviewServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideFlowProjectReviewServiceApplication.class, args);
    }

 }
