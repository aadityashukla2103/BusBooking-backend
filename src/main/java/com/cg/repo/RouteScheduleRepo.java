package com.cg.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.entity.RouteSchedule;

public interface RouteScheduleRepo extends JpaRepository<RouteSchedule, Long> {

    @Query("""
        SELECT rs
        FROM RouteSchedule rs
        WHERE LOWER(rs.route.source) = LOWER(:source)
        AND LOWER(rs.route.destination) = LOWER(:destination)
        AND rs.scheduleDate >= :date
    """)
    List<RouteSchedule> findSchedules(
            String source,
            String destination,
            LocalDate date
    );
}