package org.example.rideflowprojectentity_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan
public class RideFlowProjectEntityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideFlowProjectEntityServiceApplication.class, args);
    }

}
