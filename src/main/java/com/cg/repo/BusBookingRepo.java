package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.BusBooking;
import com.cg.entity.Customer;
import com.cg.entity.RouteSchedule;

@Repository
public interface BusBookingRepo extends JpaRepository<BusBooking, Long> {

    // My bookings
    List<BusBooking> findByCustomer(Customer customer);

    // All bookings of one schedule
    List<BusBooking> findBySchedule(RouteSchedule schedule);

}