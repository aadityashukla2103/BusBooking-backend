package com.cg.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busNumber;
    private String busType; // AC / Sleeper
    private int totalSeats;
    @JsonIgnore
    @OneToMany(mappedBy = "bus")
    private List<RouteSchedule> schedules;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public List<RouteSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<RouteSchedule> schedules) {
		this.schedules = schedules;
	}
    
}