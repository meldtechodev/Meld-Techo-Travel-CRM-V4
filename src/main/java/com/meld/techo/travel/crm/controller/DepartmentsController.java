package com.meld.techo.travel.crm.controller;

import java.util.List;
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
import com.meld.techo.travel.crm.models.Departments;
import com.meld.techo.travel.crm.security.service.DepartmentsService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/departments")
public class DepartmentsController {

	@Autowired
	private DepartmentsService departmentsService;
	
	
	// Get all/paginated
	@GetMapping("/all/paginated")
	public ResponseEntity<?> getDesignations(
	        @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {
	    try {
	        Page<Departments> departments = departmentsService.getDepartments(page, size, sortDirection);

	        Response<Page<Departments>> response = new Response<>("SUCCESS", "Designations fetched successfully", departments);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	       
	        ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), "500");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	
	//Get All
	@GetMapping("/all")
	public ResponseEntity<Response<Object>> getAllDepartments() {
	    try {
	        
	        Response<Object> response = departmentsService.getAllDepartments();
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch(Exception e) {
	        
	        throw e;
	    }
	}
	
	// Get By Id
	@GetMapping("/{id}")
	public ResponseEntity<Response<Object>> getDepartmentsId(@PathVariable Long id){
		try {
			Response<Object> response = departmentsService.getDepartmentsById(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			throw e;
			
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Response<?>> addDepartments(@RequestBody @Valid Departments departments, HttpServletRequest request){
		
		
		String clientIpAddress = getClientIp(request);
		departments.setIpaddress(clientIpAddress);
		
		try {
			
			Departments depart = departmentsService.addDepartments(departments);
			
			Response<Departments> response = new Response<>("SUCCESS", "Departments add Successfully", depart);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}catch(CustomException e) {
			
			ErrorResponse errorResponse = new ErrorResponse("FAILURE", e.getMessage(), e.getErrorCode());
			Response<ErrorResponse> response = new Response<>("FAILURE", "Validation error", errorResponse);
			
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		
		} catch(Exception e) {
			
			 ErrorResponse errorResponse = new ErrorResponse("FAILURE", "An unexpected error occurred: " + e.getMessage(), "500");
		        Response<ErrorResponse> response = new Response<>("FAILURE", "Internal server error", errorResponse);
			
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<Departments>> updateDepartment(
	        @PathVariable("id") Long id,
	        @RequestBody Departments updatedDepartment, HttpServletRequest request) {
		
		String clientIpAddress = getClientIp(request);
		updatedDepartment.setIpaddress(clientIpAddress);

	   
		Departments updated = departmentsService.updateDepartments(id, updatedDepartment);

	    if (updated != null) {
	        Response<Departments> response = new Response<>("success", "Departments updated successfully", null);
	        return ResponseEntity.ok(response);
	    } else {
	      
	        Response<Departments> response = new Response<>("error", "Departments not found", null);
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartments(@PathVariable Long id) {
        try {
            String result = departmentsService.deleteDepartments(id);
            
            return new ResponseEntity<>(new Response<>("SUCCESS", "Departments deleted successfully", result), HttpStatus.OK);
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
	

	
	
	