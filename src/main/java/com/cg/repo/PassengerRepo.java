package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Passenger;

public interface PassengerRepo extends JpaRepository<Passenger,Long>{

}
