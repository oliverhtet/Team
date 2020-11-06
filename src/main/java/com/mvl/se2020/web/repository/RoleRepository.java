package com.mvl.se2020.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvl.se2020.web.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	
}
