package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.cg.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{

	
}
