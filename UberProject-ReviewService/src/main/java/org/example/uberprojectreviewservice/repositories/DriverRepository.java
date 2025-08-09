package org.example.uberprojectreviewservice.repositories;


import org.example.uberprojectentity_service.Model.Driver;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver,Long> {

    Optional<Driver> findByIdAndLicenseNumber(Long id, String lincenseNumber);

 /*   @Query("SELECT d FROM Driver d WHERE d.id = :id AND d.licenseNumber = :ln")
    Optional<Driver> findDriverByIdAndLicenseNumber(Long id, String ln);*/

     @EntityGraph(attributePaths = {"bookings"})
     List<Driver> findAllByIdIn(List<Long> driverId);


}
