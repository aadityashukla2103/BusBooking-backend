package com.cg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cg.entity.BusBooking;
import com.cg.entity.Customer;
import com.cg.repo.BusBookingRepo;
import com.cg.repo.CustomerRepo;

@Service
public class BookingService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private BusBookingRepo bookingRepo;

    public List<BusBooking> getMyBookings() {

        // 🔑 Get username from token
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // 🔗 Fetch customer
        Customer customer = customerRepo.findByUsername(username);

        // 📦 Fetch bookings
        return bookingRepo.findByCustomer(customer);
    }
}