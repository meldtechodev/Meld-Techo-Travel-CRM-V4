package com.meld.techo.travel.crm.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.ErrorResponse;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.State;
import com.meld.techo.travel.crm.security.service.StateService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/state")
public class StateController {
	
	  @Autowired
	    private StateService stateService;
	  
	  
	  // Paginated
	  @GetMapping("/all/paginated")
	  public ResponseEntity<?> getState(
	          @RequestParam(value = "page", defaultValue = "0") int page,
	          @RequestParam(value = "size", defaultValue = "10") int size,
	          @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {

	      Page<State> statesPage = stateService.getState(page, size, sortDirection);
	      
	      if (statesPage.hasContent()) {
	          return ResponseEntity.ok(new Response<>("Successful", "States retrieved successfully", statesPage));
	      } else {
	          return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                  .body(new Response<>("Failed", "No states found", null));
	      }
	  }

	    

	 
	  
	  //Version 3 create  code
 public static String uploadDirectory=System.getProperty("user.dir") + "/src/main/image/countryimage";		
 private String timestamp;
 
 @PostMapping("")
 public ResponseEntity<?> saveState(@Valid @ModelAttribute State state, BindingResult result,
                                    @RequestParam("image") MultipartFile[] files, HttpServletRequest request) throws IOException {
	  
	 String clientIpAddress = getClientIp(request);
     state.setIpaddress(clientIpAddress);

     if (result.hasErrors()) {
         Map<String, String> errors = new HashMap<>();
         result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
         return ResponseEntity.badRequest().body(new Response<>("Failed", "Validation failed", errors));
     }

     if (stateService.existsByStateName(state.getStateName())) {
         String errorMessage = "State with the name " + state.getStateName() + " already exists.";
         String errorCode = "409";

         ErrorResponse errorResponse = new ErrorResponse("Failed", errorMessage, errorCode);

         return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
     }

     List<String> imageUrls = new ArrayList<>();

     Path uploadPath = Paths.get(uploadDirectory);
     if (!Files.exists(uploadPath)) {
         Files.createDirectories(uploadPath);
     }

     // Process each image file
     for (MultipartFile file : files) {
         if (!isValidImage(file)) {
             throw new IllegalArgumentException("File must be a JPEG or PNG image.");
         }

         String uniqueFilename = generateUniqueFilename(file.getOriginalFilename());

         Path fileNameAndPath = Paths.get(uploadDirectory, uniqueFilename);
         Files.write(fileNameAndPath, file.getBytes());

         String imageUrl = "/images/countryimage/" + uniqueFilename;
         imageUrls.add(imageUrl);
     }

     state.setSimage(imageUrls);
     stateService.createState(state);

     return ResponseEntity.ok(new Response<>("Successful", "State created successfully", null));
 }

 private boolean isValidImage(MultipartFile file) {
     String contentType = file.getContentType();
     return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
 }

 private String generateUniqueFilename(String originalFilename) {
     String extension = "";
     if (originalFilename != null && originalFilename.lastIndexOf('.') > 0) {
         extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
     }

     String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH-mm-ss"));
     String uniqueID = UUID.randomUUID().toString();

     return timestamp + "_" + uniqueID + extension;
 }
 
 
    // Update Code
	@PutMapping("/{id}")
    public ResponseEntity<Response<Object>> updateState(@PathVariable Long id,
                                                         @ModelAttribute State state,
                                                         @RequestParam(value = "image", required = false) MultipartFile[] files, HttpServletRequest request) throws IOException {
        
		String clientIpAddress = getClientIp(request);
        state.setIpaddress(clientIpAddress);
        Response<Object> response = stateService.updateStateById(id, state, files);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	

    // Delete Api
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteState(@PathVariable Long id) {
        try {
            String result = stateService.deleteState(id);
            
            return new ResponseEntity<>(new Response<>("SUCCESS", "State deleted successfully", result), HttpStatus.OK);
        } catch (CustomException e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), e.getErrorCode());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", "An unexpected error occurred: " + e.getMessage(), "500");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }}
	
	   
	   
	   // Get all Api 
	@GetMapping("/all")
    public ResponseEntity<Response<Object>> getAllStates() {
        try {
            
            Response<Object> response = stateService.getAllStates();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
           
            throw e; 
    }}
	
	    
	    // Get State By Country Id
	    @GetMapping("/country/{countryId}")
	    public ResponseEntity<Response<Object>> getStatesByCountryId(@PathVariable Long countryId) {
	    	
	    	try {
	        Response<Object> response = stateService.getStatesByCountryId(countryId);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	        
	    } catch (Exception e) {
	    	throw e;
	    }
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



	    