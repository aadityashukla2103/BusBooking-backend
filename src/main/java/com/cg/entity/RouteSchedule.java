package com.cg.entity;
import jakarta.persistence.Entity;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
public class RouteSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate scheduleDate;
    private LocalTime departureTime;

    private int totalSeats;
    private int availableSeats;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;
    @JsonProperty("route")
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    @JsonProperty("bus")
    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @JsonIgnore
    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<BusBooking> bookings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(LocalDate scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public ScheduleStatus getStatus() {
	    return status;
	}

	public void setStatus(ScheduleStatus status) {
	    this.status = status;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}


	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public List<BusBooking> getBookings() {
		return bookings;
	}

	public void setBookings(List<BusBooking> bookings) {
		this.bookings = bookings;
	}
    
}