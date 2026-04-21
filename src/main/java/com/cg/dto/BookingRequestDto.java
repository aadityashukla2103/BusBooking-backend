package com.cg.dto;
import java.util.*;

public class BookingRequestDto {

    private Long scheduleId;

    private List<PassengerDto> passengers;


	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public List<PassengerDto> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerDto> passengers) {
		this.passengers = passengers;
	}
    
}
