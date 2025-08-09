package org.example.uberprojectreviewservice;

import org.example.uberprojectentity_service.Model.Booking;
import org.example.uberprojectentity_service.Model.Review;
import org.example.uberprojectreviewservice.adapters.CreateReviewDtoToReviewAdapter;
import org.example.uberprojectreviewservice.controllers.ReviewController;
import org.example.uberprojectreviewservice.dtos.CreateReviewDto;
import org.example.uberprojectreviewservice.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @Mock
    private CreateReviewDtoToReviewAdapter createReviewDtoToReviewAdapter;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindReviewById_success(){
        long reviewId = 1L;
        Review mockReview = Review.builder().build();
        mockReview.setId(reviewId);

        //mocking
        when(reviewService.findReviewById(reviewId)).thenReturn(Optional.of(mockReview));

        //performing the test
        ResponseEntity<?> response = reviewController.findReviewById(reviewId);

        //assertions
        assertEquals(HttpStatus.OK,response.getStatusCode());
        Optional<Review> returnedReview = (Optional<Review>) response.getBody();
        assertEquals(reviewId,returnedReview.get().getId());

    }

    @Test
    public void testPublishReview_success(){

        CreateReviewDto requestDto = new CreateReviewDto();
        Booking booking = new Booking();
        booking.setId(1L);
        requestDto.setBookingId(booking.getId());

        Review incomingReview = Review.builder()
                .content("Nice")
                .rating(4.5)
                .booking(booking)
                .build();

        //When it will request review,then, it will pass this dummy review
        when(createReviewDtoToReviewAdapter.convertDto(requestDto)).thenReturn(incomingReview);

        //this will be the returned review received from client
        Review savedReview =Review.builder()
                .rating(incomingReview.getRating())
                .booking(incomingReview.getBooking())
                .rating(incomingReview.getRating())
                .build();

        //This will check if everything is ok or not
        when(reviewService.publishReview(incomingReview)).thenReturn(savedReview);
        ResponseEntity<?> response = reviewController.publishReview(requestDto);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

}
