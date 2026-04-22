package com.cg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cg.dto.BookingRequestDto;
import com.cg.dto.BookingResponseDto;
import com.cg.dto.PassengerDto;
import com.cg.dto.SeatResponseDto;
import com.cg.entity.BusBooking;
import com.cg.entity.Customer;
import com.cg.entity.Passenger;
import com.cg.entity.RouteSchedule;
import com.cg.repo.BusBookingRepo;
import com.cg.repo.CustomerRepo;
import com.cg.repo.PassengerRepo;
import com.cg.repo.RouteScheduleRepo;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private BusBookingRepo bookingRepo;

    @Mock
    private RouteScheduleRepo scheduleRepo;

    @Mock
    private PassengerRepo passengerRepo;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setup() {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("aaditya", null)
        );
    }
    
    

    @Test
    void testGetMyBookings() {
        Customer customer = new Customer();
        customer.setUsername("aaditya");
        BusBooking booking = new BusBooking();
        booking.setId(1L);
        booking.setBookingDate(LocalDateTime.now());

        when(customerRepo.findByUsername("aaditya"))
                .thenReturn(customer);
        when(bookingRepo.findByCustomer(customer))
                .thenReturn(new ArrayList<>(List.of(booking)));
        List<BusBooking> result = bookingService.getMyBookings();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(customerRepo, times(1))
                .findByUsername("aaditya");
        verify(bookingRepo, times(1))
                .findByCustomer(customer);
    }

    @Test
    void testCreateBooking() {

        Customer customer = new Customer();

        RouteSchedule schedule = new RouteSchedule();
        schedule.setId(1L);
        schedule.setAvailableSeats(10);

        BusBooking savedBooking = new BusBooking();
        savedBooking.setId(100L);

        PassengerDto p1 = new PassengerDto();
        p1.setName("Ram");
        p1.setAge(22);
        p1.setSeatNo(1);

        PassengerDto p2 = new PassengerDto();
        p2.setName("Shyam");
        p2.setAge(25);
        p2.setSeatNo(2);

        BookingRequestDto request = new BookingRequestDto();
        request.setScheduleId(1L);
        request.setPassengers(Arrays.asList(p1, p2));

        when(customerRepo.findByUsername("aaditya")).thenReturn(customer);
        when(scheduleRepo.findById(1L))
                .thenReturn(Optional.of(schedule));
        when(bookingRepo.save(any(BusBooking.class)))
                .thenReturn(savedBooking);

        BookingResponseDto response =
                bookingService.createBooking(request);

        assertEquals(100L, response.getBookingId());
        assertEquals("CONFIRMED", response.getStatus());
        assertEquals(1000, response.getTotalAmount());

        verify(passengerRepo, times(2))
                .save(any(Passenger.class));

        verify(scheduleRepo).save(schedule);
    }

    @Test
    void testCreateBookingSeatsNotAvailable() {

        RouteSchedule schedule = new RouteSchedule();
        schedule.setAvailableSeats(1);

        PassengerDto p1 = new PassengerDto();
        PassengerDto p2 = new PassengerDto();

        BookingRequestDto request = new BookingRequestDto();
        request.setScheduleId(1L);
        request.setPassengers(Arrays.asList(p1, p2));

        when(customerRepo.findByUsername("aaditya"))
                .thenReturn(new Customer());

        when(scheduleRepo.findById(1L))
                .thenReturn(Optional.of(schedule));

        RuntimeException ex = assertThrows(
            RuntimeException.class,
            () -> bookingService.createBooking(request)
        );

        assertEquals("Seats not available", ex.getMessage());
    }

    @Test
    void testGetSeatDetails() {

        RouteSchedule schedule = new RouteSchedule();
        schedule.setId(1L);
        schedule.setTotalSeats(40);
        schedule.setAvailableSeats(38);

        Passenger p1 = new Passenger();
        p1.setSeatNo(1);

        Passenger p2 = new Passenger();
        p2.setSeatNo(2);

        BusBooking booking = new BusBooking();
        booking.setPassengers(List.of(p1, p2));

        when(scheduleRepo.findById(1L))
                .thenReturn(Optional.of(schedule));

        when(bookingRepo.findBySchedule(schedule))
                .thenReturn(List.of(booking));

        SeatResponseDto result =
                bookingService.getSeatDetails(1L);

        assertEquals(40, result.getTotalSeats());
        assertEquals(38, result.getAvailableSeats());
        assertEquals(List.of(1, 2), result.getBookedSeats());
    }
}