package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.entity.Passenger;
public interface PassengerRepo extends JpaRepository<Passenger, Long> {

    @Query("SELECT p.seatNo FROM Passenger p WHERE p.booking.schedule.id = :scheduleId")
    List<Integer> findBookedSeats(Long scheduleId);
}
