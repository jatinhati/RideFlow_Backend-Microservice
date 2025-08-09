package org.example.uberprojectentity_service.Model;


import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExactLocation extends BaseModel {

    private Double latitude;
    private Double longitude;
}