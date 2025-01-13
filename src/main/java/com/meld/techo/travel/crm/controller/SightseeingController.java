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
import com.meld.techo.travel.crm.models.Sightseeing;
import com.meld.techo.travel.crm.security.service.SightseeingService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/sightseeing")
public class SightseeingController {

    @Autowired
    private SightseeingService sightseeingService;

    // Get sightseeing by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<Sightseeing>> getSightseeingById(@PathVariable Long id) {
        Optional<Sightseeing> sightseeing = sightseeingService.getSightseeingById(id);
        return sightseeing.map(value -> ResponseEntity.ok(
                    new Response<>("success", "Sightseeing fetched successfully.", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Response<>("error", "Sightseeing not found.", null)));
    }

    // Get all sightseeing with pagination and sorting
    @GetMapping("/all/paginated")
    public ResponseEntity<Response<Page<Sightseeing>>> getSightseeing(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {
        Page<Sightseeing> sightseeingPage = sightseeingService.getSightseeing(page, size, sortDirection);

        if (sightseeingPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Response<>("error", "No sightseeing records found.", Page.empty()));
        }

        return ResponseEntity.ok(
                new Response<>("success", "Sightseeing records fetched successfully.", sightseeingPage));
    }

    // Create new sightseeing
    @PostMapping("")
    public ResponseEntity<Response<Sightseeing>> addSightseeing(@RequestBody Sightseeing sightseeing, HttpServletRequest request) {
        try {
        	
        	String clientIpAddress = getClientIp(request);
        	sightseeing.setIpaddress(clientIpAddress);
	        
            Sightseeing createdSightseeing = sightseeingService.addSightseeing(sightseeing);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new Response<>("success", "Sightseeing created successfully.", createdSightseeing));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>("error", "An error occurred while creating sightseeing.", null));
        }
    }

    // Update sightseeing by ID
    @PutMapping("/{id}")
    public ResponseEntity<Response<Sightseeing>> updateSightseeing(@PathVariable Long id, @RequestBody Sightseeing sightseeing, HttpServletRequest request) {
    	
    	String clientIpAddress = getClientIp(request);
    	sightseeing.setIpaddress(clientIpAddress);
        
        Optional<Sightseeing> existingSightseeing = sightseeingService.getSightseeingById(id);

        if (existingSightseeing.isPresent()) {
            Sightseeing updatedSightseeing = existingSightseeing.get();
            updatedSightseeing.setTitle(sightseeing.getTitle());
            updatedSightseeing.setIpaddress(sightseeing.getIpaddress());
            updatedSightseeing.setStatus(sightseeing.isStatus());
            updatedSightseeing.setIsdelete(sightseeing.isIsdelete());
            updatedSightseeing.setCreatedby(sightseeing.getCreatedby());
            updatedSightseeing.setModifiedby(sightseeing.getModifiedby());
            updatedSightseeing.setCreateddate(sightseeing.getCreateddate());
            updatedSightseeing.setModifieddate(sightseeing.getModifieddate());

            sightseeingService.updateSightseeing(updatedSightseeing);

            return ResponseEntity.ok(
                    new Response<>("success", "Sightseeing updated successfully.", updatedSightseeing));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Response<>("error", "Sightseeing not found.", null));
        }
    }

    // Delete sightseeing by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSightseeing(@PathVariable Long id) {
        try {
            String result = sightseeingService.deleteSightseeing(id);
            
            return new ResponseEntity<>(new Response<>("SUCCESS", "Sightseeing deleted successfully", result), HttpStatus.OK);
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
    
