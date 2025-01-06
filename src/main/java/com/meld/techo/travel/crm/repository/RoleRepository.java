package com.meld.techo.travel.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.Role;


 @Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    
	 Optional<Role> findByRoleName(String roleName);
	  
}

