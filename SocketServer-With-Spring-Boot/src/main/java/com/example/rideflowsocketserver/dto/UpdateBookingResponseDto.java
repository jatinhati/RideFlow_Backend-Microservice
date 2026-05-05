package com.example.rideflowsocketserver.dto;

import com.example.rideflowprojectentityservice.models.BookingStatus;
import com.example.rideflowprojectentityservice.models.Driver;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingResponseDto {

    private Long bookingId;
    private BookingStatus status;
    private Optional<Driver> driver;
}
