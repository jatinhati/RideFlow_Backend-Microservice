package org.example.rideflowprojectreviewservice.repositories;



import org.example.rideflowprojectentity_service.Model.Booking;
import org.example.rideflowprojectentity_service.Model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findAllByDriverIn(List<Driver> drivers);


}
