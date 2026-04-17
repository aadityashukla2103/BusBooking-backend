package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Role;
import com.cg.entity.RolePk;

@Repository
public interface RoleRepo extends JpaRepository<Role, RolePk>{
	
}




