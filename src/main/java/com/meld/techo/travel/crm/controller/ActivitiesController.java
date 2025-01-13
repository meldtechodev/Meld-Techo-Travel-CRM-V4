package com.meld.techo.travel.crm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.ErrorResponse;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Activities;
import com.meld.techo.travel.crm.security.service.ActivitiesService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/activities")
public class ActivitiesController {
	
	
	@Autowired
	private ActivitiesService  activitiesService;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<Activities>> getActivitiesById(@PathVariable Long id) {
	    try {
	        // Fetch the activity by ID
	        Optional<Activities> activities = activitiesService.getActivitiesById(id);

	        // If the activity is found, wrap it in the Response object
	        if (activities.isPresent()) {
	            Response<Activities> response = new Response<>(
	                "success",
	                "Activity fetched successfully",
	                activities.get()
	            );
	            return ResponseEntity.ok(response);
	        } else {
	            // If not found, throw a custom exception
	            throw new CustomException("Activity with ID " + id + " not found", "404");
	        }
	    } catch (Exception ex) {
	        // Log exception details (optional)
	        ex.printStackTrace();

	        // Rethrow the exception to be handled by GlobalExceptionHandler
	        throw ex;
	    }
	}

	
	
//	@GetMapping("/getAll")
//	public List<Activities> getAllActivities(){
//		return activitiesService.getAllActivities();
//	}
	
	
	
	@GetMapping("/all/paginated")
	public ResponseEntity<Response<Page<Activities>>> getActivities(
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {
	    try {
	        // Fetch paginated activities
	        Page<Activities> activitiesPage = activitiesService.getActivities(page, size, sortDirection);

	        // Wrap the result in a custom Response object
	        Response<Page<Activities>> response = new Response<>(
	            "success",
	            "Activities fetched successfully",
	            activitiesPage
	        );

	        // Return the Response object wrapped in a ResponseEntity
	        return ResponseEntity.ok(response);
	    } catch (Exception ex) {
	        // Log exception details (optional)
	        ex.printStackTrace();

	        // Rethrow the exception to be handled by GlobalExceptionHandler
	        throw ex;
	    }
	}

	
	
//	@PostMapping("/create")
//	public Activities addActivities(@RequestBody Activities activities) {
//		return this.activitiesService.addActivities(activities);
//	}
//	
	
	
	
	@PostMapping("")
	public ResponseEntity<Response<Activities>> addActivities(@RequestBody Activities activities, HttpServletRequest request) {
	    try {
	        // Check if an activity with the same title already exists
	        if (activitiesService.existsByTitle(activities.getTitle())) {
	            throw new CustomException("Activities with this title '" + activities.getTitle() + "' already exist.", "409");
	        }

	        String clientIpAddress = getClientIp(request);
	        activities.setIpaddress(clientIpAddress);
	        // Save the new activity
	        Activities createdActivity = activitiesService.addActivities(activities);

	        // Wrap the success response in the custom Response<T> object
	        Response<Activities> response = new Response<>(
	            "success",
	            "Activities successfully created",
	            createdActivity
	        );

	        // Return the Response<T> wrapped in a ResponseEntity
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    } catch (Exception ex) {
	        // Log exception details (optional)
	        ex.printStackTrace();

	        // Rethrow the exception to be handled by GlobalExceptionHandler
	        throw ex;
	    }
	}


	
	
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Activities> updateActivities(@PathVariable Long id , @RequestBody Activities activities, HttpServletRequest request){
		 
		
		String clientIpAddress = getClientIp(request);
		activities.setIpaddress(clientIpAddress);
		if(activities != null)
		{
			Activities ai = new Activities();
			
			ai.setId(id);
			
			ai.setTitle(activities.getTitle());
			ai.setIpaddress(activities.getIpaddress());
			ai.setStatus(activities.isStatus());
			ai.setIsdelete(activities.isIsdelete());
			ai.setCreatedby(activities.getCreatedby());
			ai.setModifiedby(activities.getModifiedby());
			ai.setCreateddate(activities.getCreateddate());
			ai.setModifieddate(activities.getModifieddate());
			
		
		
			activitiesService.updateActivities(ai);
		return ResponseEntity.ok(ai);
	}

	else
	{
		return ResponseEntity.notFound().build();
	}
	}
	
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivities(@PathVariable Long id) {
        try {
            String result = activitiesService.deleteActivities(id);
            
            return new ResponseEntity<>(new Response<>("SUCCESS", "Activities deleted successfully", result), HttpStatus.OK);
        } catch (CustomException e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), e.getErrorCode());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", "An unexpected error occurred: " + e.getMessage(), "500");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }}	
	
	
	// Helper method to fetch client IP address
    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}

