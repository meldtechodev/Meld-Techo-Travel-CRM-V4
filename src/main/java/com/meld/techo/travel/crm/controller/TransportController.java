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
import com.meld.techo.travel.crm.models.Transport;
import com.meld.techo.travel.crm.security.service.TransportService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/transport")
public class TransportController {

    @Autowired
    private TransportService transportService;

    @GetMapping("/all/paginated")
    public ResponseEntity<Response<Page<Transport>>> getTransport(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {
        try {
            // Validate sort direction
            if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
                throw new IllegalArgumentException("Invalid sort direction. It must be 'asc' or 'desc'.");
            }

            // Fetch transport data with pagination
            Page<Transport> transportPage = transportService.getTransport(page, size, sortDirection);

            // Check if the page is empty
            if (transportPage.isEmpty()) {
                throw new CustomException("No transport records found", "404");
            }

            // Wrap successful response in a standardized format
            Response<Page<Transport>> response = new Response<>(
                "success",
                "Transport records fetched successfully",
                transportPage
            );

            // Return the Response object wrapped in a ResponseEntity
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Handle invalid input
            Response<Page<Transport>> errorResponse = new Response<>(
                "error",
                e.getMessage(),
                Page.empty() // Empty page in error response
            );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (CustomException e) {
            // Handle no records found
            Response<Page<Transport>> errorResponse = new Response<>(
                "error",
                e.getMessage(),
                Page.empty() // Empty page in error response
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception ex) {
            // Log unexpected errors (optional for debugging)
            ex.printStackTrace();

            // Handle unexpected errors
            Response<Page<Transport>> errorResponse = new Response<>(
                "error",
                "An unexpected error occurred while fetching transport records",
                Page.empty() // Empty page in error response
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("")
    public ResponseEntity<Response<Transport>> addTransport(@RequestBody Transport transport, HttpServletRequest request) {
        try {
        	
        	
        	String clientIpAddress = getClientIp(request);
        	transport.setIpaddress(clientIpAddress);
            // Save the transport record
            Transport savedTransport = transportService.addTransport(transport);

            // Wrap successful response in a standardized format
            Response<Transport> response = new Response<>(
                "success",
                "Transport record created successfully",
                savedTransport
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            Response<Transport> errorResponse = new Response<>(
                "error",
                e.getMessage(),
                null // No data in error response
            );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception ex) {
            // Log unexpected errors (optional for debugging)
            ex.printStackTrace();

            // Handle unexpected errors
            Response<Transport> errorResponse = new Response<>(
                "error",
                "An unexpected error occurred while creating the transport record",
                null // No data in error response
            );

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

