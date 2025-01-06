package com.meld.techo.travel.crm.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meld.techo.travel.crm.config.JwtUtils;
import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.CustomMessageAuth;
import com.meld.techo.travel.crm.dto.UserUpdateDTO;
import com.meld.techo.travel.crm.models.User;
import com.meld.techo.travel.crm.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("Motherson/crm/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Autowired
	    private JwtUtils jwtUtils;

	    @PostMapping("/register")
	    public ResponseEntity<?> register(@RequestBody User user,HttpServletRequest request) {
	        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
	        	
	        	CustomMessageAuth response =new CustomMessageAuth();
	        	response.setMessage("user already exist!!!!");
	        	response.setSucess(false);
	        	
	        	 return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	        }
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        // Fetch and set IP address
	        String clientIpAddress = getClientIp(request);
	        user.setIpaddres(clientIpAddress);
	        
	        
	        userRepository.save(user);
	        CustomMessageAuth response =new CustomMessageAuth();
	        response.setMessage("User registered successfully");
	        response.setSucess(true);
	        
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }

	    @PutMapping("/signupsuperadmin")
	    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO updateUserDTO) {
	        

	        // Fetch the user with ID 1
	        Optional<User> optionalUser = userRepository.findById(1L);
	        if (optionalUser.isEmpty()) {
	            
	        	throw  new CustomException("User not found !!!","404");
	        }

	        User existingUser = optionalUser.get();

	        // Update the specified fields
	        if (updateUserDTO.getName() != null) {
	            existingUser.setName(updateUserDTO.getName());
	        }

	        if (updateUserDTO.getEmail() != null) {
	            existingUser.setEmail(updateUserDTO.getEmail());
	        }

	        if (updateUserDTO.getPassword() != null) {
	            existingUser.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
	        }

	        userRepository.save(existingUser);
	        CustomMessageAuth response =new CustomMessageAuth();
	        response.setMessage("User registered successfully");
	        response.setSucess(true);
	        
	        return new ResponseEntity<>(response, HttpStatus.CREATED);

	        
	    }

	   
	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody User user) {
	        User existingUser = userRepository.findByEmail(user.getEmail())
	                .orElseThrow(() -> new CustomException("User not found","404"));
	        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	        }
	        String token = jwtUtils.generateToken(existingUser.getEmail());
	        return ResponseEntity.ok(token);
	    }
	    
	    @GetMapping("/user")
	   public ResponseEntity<?>getUserDetails(@RequestHeader("Authorization") String authorizationHeader)
	   {
		   
		   if(authorizationHeader==null||!authorizationHeader.startsWith("Bearer "))
		   {
			   
			   CustomMessageAuth response =new CustomMessageAuth();
		        response.setMessage("invalid or missing token!!!!!");
		        response.setSucess(false);
		        
		        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		   }
		     
		  String token= authorizationHeader.substring(7);
		  
		  if(!jwtUtils.validateToken(token))
		  {
			  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
		  }
		  
		  String username = jwtUtils.getUsernameFromToken(token);
	        User user = userRepository.findByEmail(username)
	                .orElseThrow(() -> new CustomException("User not found","404"));

	        return ResponseEntity.ok(user);
		   
		
		   
	   }
	    
	    
	    // Helper method to fetch client IP address
	    private String getClientIp(HttpServletRequest request) {
	        String ipAddress = request.getHeader("X-Forwarded-For");
	        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
	            ipAddress = request.getRemoteAddr();
	        }
	        return ipAddress;
	    }
	    
	    
	    
	    
	    
	    
	    
	    

}
