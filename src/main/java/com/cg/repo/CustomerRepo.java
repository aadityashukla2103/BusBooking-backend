package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer,Long>{

}
