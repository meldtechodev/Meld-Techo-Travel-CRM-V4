package com.meld.techo.travel.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Inclusion;
import com.meld.techo.travel.crm.security.service.InclusionService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/inclusion")

public class InclusionController {
	
	@Autowired 
	 
	private InclusionService inclusionservice;
   
	@PostMapping("")
	public ResponseEntity<Response<Inclusion>> createInclusion(@RequestBody Inclusion inclusion, HttpServletRequest request) {
		
		String clientIpAddress = getClientIp(request);
		inclusion.setIpaddress(clientIpAddress);
	    try {
	        // Add the inclusion using the service
	        Inclusion savedInclusion = inclusionservice.createInclusionser(inclusion);

	        // Wrap the successful response in a Response object
	        Response<Inclusion> response = new Response<>(
	            "success",
	            "Inclusion created successfully",
	            savedInclusion
	        );

	        // Return the Response object wrapped in a ResponseEntity with CREATED status
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    } catch (IllegalArgumentException e) {
	        // Handle known validation error (e.g., conflict or invalid data)
	        Response<Inclusion> errorResponse = new Response<>(
	            "error",
	            e.getMessage(),
	            null // No data in error response
	        );

	        // Return CONFLICT status (409) or BAD_REQUEST depending on the situation
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    } catch (Exception ex) {
	        // Log the exception (optional for debugging purposes)
	        ex.printStackTrace();

	        // Handle unexpected errors (e.g., database issues, etc.)
	        Response<Inclusion> errorResponse = new Response<>(
	            "error",
	            "An unexpected error occurred while creating the inclusion",
	            null // No data in error response
	        );

	        // Return INTERNAL_SERVER_ERROR status (500)
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

	 
	@GetMapping("/all/paginated")
	public ResponseEntity<Response<Page<Inclusion>>> getInclusion(
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {
	    try {
	        // Validate sort direction to be either 'asc' or 'desc'
	        if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
	            throw new IllegalArgumentException("Invalid sort direction. It must be 'asc' or 'desc'.");
	        }

	        // Fetch the inclusion page with pagination and sorting
	        Page<Inclusion> inclusionPage = inclusionservice.getInclusion(page, size, sortDirection);

	        // Check if the page is empty
	        if (inclusionPage.isEmpty()) {
	            throw new CustomException("No inclusions found", "404");
	        }

	        // Wrap the successful response in a Response object
	        Response<Page<Inclusion>> response = new Response<>(
	            "success",
	            "Inclusions fetched successfully",
	            inclusionPage
	        );

	        // Return the Response object wrapped in a ResponseEntity
	        return ResponseEntity.ok(response);
	    } catch (IllegalArgumentException e) {
	        // Handle invalid sort direction
	        Response<Page<Inclusion>> errorResponse = new Response<>(
	            "error",
	            e.getMessage(),
	            Page.empty() // Empty page in error response
	        );

	        // Return BAD_REQUEST status (400)
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    } catch (CustomException e) {
	        // Handle case where no inclusions are found
	        Response<Page<Inclusion>> errorResponse = new Response<>(
	            "error",
	            e.getMessage(),
	            Page.empty() // Empty page in error response
	        );

	        // Return NOT_FOUND status (404)
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	    } catch (Exception ex) {
	        // Log unexpected exception (optional)
	        ex.printStackTrace();

	        // Handle unexpected errors
	        Response<Page<Inclusion>> errorResponse = new Response<>(
	            "error",
	            "An unexpected error occurred while fetching inclusions",
	            Page.empty() // Empty page in error response
	        );

	        // Return INTERNAL_SERVER_ERROR status (500)
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
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

