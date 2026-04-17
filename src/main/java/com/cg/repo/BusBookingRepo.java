package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.BusBooking;

public interface BusBookingRepo extends JpaRepository<BusBooking, Long> {
}
