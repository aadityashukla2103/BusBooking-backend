package com.cg.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cg.dto.BookingRequestDto;
import com.cg.dto.BookingResponseDto;
import com.cg.dto.SeatResponseDto;
import com.cg.entity.BusBooking;
import com.cg.entity.Customer;
import com.cg.entity.Passenger;
import com.cg.entity.RouteSchedule;
import com.cg.repo.BusBookingRepo;
import com.cg.repo.CustomerRepo;
import com.cg.repo.PassengerRepo;
import com.cg.repo.RouteScheduleRepo;

@Service
public class BookingService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private BusBookingRepo bookingRepo;

    @Autowired
    private RouteScheduleRepo scheduleRepo;

    @Autowired
    private PassengerRepo passengerRepo;

    public List<BusBooking> getMyBookings() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Customer customer = customerRepo.findByUsername(username);

        List<BusBooking> bookings = bookingRepo.findByCustomer(customer);

        bookings.sort((b1, b2) -> 
            b2.getBookingDate().compareTo(b1.getBookingDate())
        );

        return bookings;
    }

    public BookingResponseDto createBooking(BookingRequestDto dto) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Customer customer = customerRepo.findByUsername(username);

        RouteSchedule schedule = scheduleRepo.findById(dto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        int seatsRequested = dto.getPassengers().size();

        if (schedule.getAvailableSeats() < seatsRequested) {
            throw new RuntimeException("Seats not available");
        }

        BusBooking booking = new BusBooking();

        booking.setBookingDate(LocalDateTime.now());
        booking.setBookingStatus("CONFIRMED");
        booking.setCustomer(customer);
        booking.setSchedule(schedule);


        booking = bookingRepo.save(booking);

        for (var p : dto.getPassengers()) {

            Passenger passenger = new Passenger();
            passenger.setName(p.getName());
            passenger.setAge(p.getAge());
            passenger.setSeatNo(p.getSeatNo());
            passenger.setBooking(booking);

            passengerRepo.save(passenger);
        }

        schedule.setAvailableSeats(
                schedule.getAvailableSeats() - seatsRequested
        );

        scheduleRepo.save(schedule);

        BookingResponseDto res = new BookingResponseDto();

        res.setBookingId(booking.getId());
        res.setStatus("CONFIRMED");

        res.setSeatNumbers(
                dto.getPassengers()
                        .stream()
                        .map(x -> x.getSeatNo())
                        .collect(Collectors.toList())
        );

        res.setTotalAmount(
                seatsRequested * 500
        );

        return res;
    }
    
    public SeatResponseDto getSeatDetails(Long scheduleId) {

        RouteSchedule schedule =
            scheduleRepo.findById(scheduleId).get();

        List<BusBooking> bookings =
            bookingRepo.findBySchedule(schedule);

        List<Integer> bookedSeats = new ArrayList<>();

        for (BusBooking b : bookings) {
            for (Passenger p : b.getPassengers()) {
                bookedSeats.add(p.getSeatNo());
            }
        }

        SeatResponseDto dto = new SeatResponseDto();
        dto.setTotalSeats(schedule.getTotalSeats());
        dto.setAvailableSeats(schedule.getAvailableSeats());
        dto.setBookedSeats(bookedSeats);

        return dto;
    }
}