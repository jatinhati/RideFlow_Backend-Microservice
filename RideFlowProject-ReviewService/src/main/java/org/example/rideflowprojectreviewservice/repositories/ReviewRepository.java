package org.example.rideflowprojectreviewservice.repositories;


import org.example.rideflowprojectentity_service.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Integer countAllByRatingIsLessThanEqual(Integer givenRating);

    List<Review> findAllByRatingIsLessThanEqual(Integer givenRating);

    List<Review> findAllByCreatedAtBefore(Date date);

    @Query("SELECT r FROM Review r WHERE r.booking.id = :bookingId")
    Review findReviewByBookingId(Long bookingId);

}

//this is to write custom complex query
//class custom {
//    public Review review;
//    public Passenger passenger;
//    public Driver driver;
//
//    public custom(Review review, Passenger passenger, Driver driver) {
//        this.review =review;
//        this.driver=driver;
//        this.passenger=passenger;
//
//    }
//}

