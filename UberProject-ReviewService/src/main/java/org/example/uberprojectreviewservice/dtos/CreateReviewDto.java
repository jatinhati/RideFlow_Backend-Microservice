package org.example.uberprojectreviewservice.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

@Getter
@Setter

public class CreateReviewDto implements Serializable {
   private String content;

    private Double rating;

    private Long bookingId;
}