package com.cg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.Customer;
import com.cg.repo.CustomerRepo;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepo customerRepo;
    @GetMapping("/profile")
    public Customer getProfile() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("AUTH: " + auth);
        System.out.println("USERNAME: " + auth.getName());

        Customer customer = customerRepo.findByUsername(auth.getName());

        System.out.println("CUSTOMER: " + customer);

        return customer;
    }
}