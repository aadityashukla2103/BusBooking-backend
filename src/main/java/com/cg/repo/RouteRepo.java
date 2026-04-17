package com.cg.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Route;

public interface RouteRepo extends JpaRepository<Route, Long> {

    Optional<Route> findBySourceAndDestination(String source, String destination);
}