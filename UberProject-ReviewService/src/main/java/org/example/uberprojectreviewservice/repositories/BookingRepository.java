package org.example.uberprojectreviewservice.repositories;



import org.example.uberprojectentity_service.Model.Booking;
import org.example.uberprojectentity_service.Model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findAllByDriverIn(List<Driver> drivers);


}
