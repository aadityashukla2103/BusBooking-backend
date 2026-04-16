package com.cg.entity;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;
    private String destination;

    @OneToMany(mappedBy = "route")
    private List<RouteSchedule> schedules;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<RouteSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<RouteSchedule> schedules) {
		this.schedules = schedules;
	}
    
}