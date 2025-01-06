package com.meld.techo.travel.crm.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.models.User;
import com.meld.techo.travel.crm.repository.UserRepository;



@Service
public class CustomUserDetailsService implements UserDetailsService {
    
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	   User user=	userRepository.findByEmail(username).orElseThrow(()->new CustomException("User not found with username!!!!","404")); 
		             
		return new CustomUserDetails(user);
	}

}
