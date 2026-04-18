package com.cg.web;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.Bus;
import com.cg.entity.Route;
import com.cg.entity.RouteSchedule;
import com.cg.repo.BusRepo;
import com.cg.repo.RouteRepo;
import com.cg.repo.RouteScheduleRepo;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private RouteScheduleRepo scheduleRepo;
    @Autowired
    private BusRepo busRepo;

    @Autowired
    private RouteRepo routeRepo;

    @PostMapping
    public RouteSchedule addSchedule(@RequestBody RouteSchedule schedule) {

        if (schedule.getRoute() == null || schedule.getRoute().getId() == null) {
            throw new RuntimeException("Route ID missing");
        }

        if (schedule.getBus() == null || schedule.getBus().getId() == null) {
            throw new RuntimeException("Bus ID missing");
        }

        Route route = routeRepo.findById(schedule.getRoute().getId())
                .orElseThrow(() -> new RuntimeException("Route not found"));

        Bus bus = busRepo.findById(schedule.getBus().getId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        schedule.setRoute(route);
        schedule.setBus(bus);

        schedule.setAvailableSeats(schedule.getTotalSeats());

        return scheduleRepo.save(schedule);
    }
    @GetMapping("/search")
    public List<RouteSchedule> search(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam String date) {

        return scheduleRepo
            .findSchedules(
                source,
                destination,
                LocalDate.parse(date)
            );
    }
}