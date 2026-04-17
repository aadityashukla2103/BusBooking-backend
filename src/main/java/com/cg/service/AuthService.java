package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.dto.SignUpDto;
import com.cg.entity.Customer;
import com.cg.entity.Role;
import com.cg.entity.RolePk;
import com.cg.entity.User;
import com.cg.repo.CustomerRepo;
import com.cg.repo.RoleRepo;
import com.cg.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String register(SignUpDto dto) {

        if (userRepo.existsById(dto.getUsername())) {
            return "Username already exists";
        }

        User user = new User();
        user.setUserName(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        userRepo.save(user);

        RolePk pk = new RolePk();
        pk.setUserName(dto.getUsername());
        pk.setRoleName("ROLE_USER");

        Role role = new Role();
        role.setKey(pk);
        roleRepo.save(role);

       
        Customer customer = new Customer();
        customer.setUsername(dto.getUsername());
        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        customerRepo.save(customer);

        return "User Registered Successfully";
    }
}