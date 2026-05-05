package org.example.rideflowprojectreviewservice.adapters;



import org.example.rideflowprojectentity_service.Model.Review;
import org.example.rideflowprojectreviewservice.dtos.CreateReviewDto;

public interface CreateReviewDtoToReviewAdapter {
    public Review convertDto(CreateReviewDto createReviewDto);
}
