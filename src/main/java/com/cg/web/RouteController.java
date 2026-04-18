package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cg.entity.Route;
import com.cg.repo.RouteRepo;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteRepo routeRepo;

    @PostMapping
    public Route addRoute(@RequestBody Route route) {
        return routeRepo.save(route);
    }

    @GetMapping
    public List<Route> getAllRoutes() {
        return routeRepo.findAll();
    }
}