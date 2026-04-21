package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.BookingRequestDto;
import com.cg.dto.BookingResponseDto;
import com.cg.dto.SeatResponseDto;
import com.cg.entity.BusBooking;
import com.cg.service.BookingService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking")
    public BookingResponseDto createBooking(
            @RequestBody BookingRequestDto dto) {

        return bookingService.createBooking(dto);
    }

    @GetMapping("/my-bookings")
    public List<BusBooking> getMyBookings() {
        return bookingService.getMyBookings();
    }
    
    @GetMapping("/seats/{id}")
    public SeatResponseDto getSeats(@PathVariable Long id) {
        return bookingService.getSeatDetails(id);
    }
}