package org.example.uberprojectreviewservice.adapters;



import org.example.uberprojectentity_service.Model.Review;
import org.example.uberprojectreviewservice.dtos.CreateReviewDto;

public interface CreateReviewDtoToReviewAdapter {
    public Review convertDto(CreateReviewDto createReviewDto);
}
