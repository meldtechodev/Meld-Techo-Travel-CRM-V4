package com.meld.techo.travel.crm.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meld.techo.travel.crm.models.User;



public interface UserRepository  extends JpaRepository<User,Long> {
	
	Optional<User>findByEmail(String email);

}

