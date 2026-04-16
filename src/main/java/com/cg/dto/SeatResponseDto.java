package com.cg.dto;

import java.util.List;

public class SeatResponseDto {

    private int totalSeats;
    private int availableSeats;
    private List<Integer> bookedSeats;
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
	public List<Integer> getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(List<Integer> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

    
}
