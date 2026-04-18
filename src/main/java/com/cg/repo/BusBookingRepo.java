package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.BusBooking;
import com.cg.entity.Customer;

public interface BusBookingRepo extends JpaRepository<BusBooking, Long> {
	List<BusBooking> findByCustomer(Customer customer);
}
