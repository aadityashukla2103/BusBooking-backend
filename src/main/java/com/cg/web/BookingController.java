package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.BusBooking;
import com.cg.service.BookingService;

@RestController
@CrossOrigin(origins= {"http://localhost:4200"})
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/my-bookings")
    public List<BusBooking> getMyBookings() {
        return bookingService.getMyBookings();
    }
}