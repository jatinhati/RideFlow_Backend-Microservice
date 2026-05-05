package com.example.rideflowbookingservice.services;


import com.example.rideflowbookingservice.dto.CreateBookingDto;
import com.example.rideflowbookingservice.dto.CreateBookingResponseDto;
import com.example.rideflowbookingservice.dto.UpdateBookingRequestDto;
import com.example.rideflowbookingservice.dto.UpdateBookingResponseDto;
import com.example.rideflowprojectentityservice.models.Booking;

public interface BookingService {

    CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails);

    UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto bookingRequestDto, Long bookingId);
}
