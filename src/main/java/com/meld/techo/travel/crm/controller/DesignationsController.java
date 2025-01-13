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
import com.meld.techo.travel.crm.dto.DesignationsDTO;
import com.meld.techo.travel.crm.dto.ErrorResponse;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Departments;
import com.meld.techo.travel.crm.models.Designations;
import com.meld.techo.travel.crm.security.service.DepartmentsService;
import com.meld.techo.travel.crm.security.service.DesignationsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
 

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/designations")
public class DesignationsController {

	@Autowired
	private DesignationsService designationsService;

	@Autowired
	private DepartmentsService departmentsService;


 
	
	@GetMapping("/all/paginated")
	public ResponseEntity<?> getDesignations(
			  @RequestParam(value = "page", defaultValue = "0") int page,
			  @RequestParam(value = "size", defaultValue = "10") int size,
			  @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection){
		try {
			Page<DesignationsDTO> designation = designationsService.getDesignations(page, size, sortDirection);
			Response<Page<DesignationsDTO>> response = new Response<>("SUCCESS", "Destinations fetched successfully", designation);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			
			ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), "500");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
			
		}
	}


	@GetMapping("/all")
    public ResponseEntity<Response<Object>> getAllDesignations() {
        try {
            
            Response<Object> response = designationsService.getAllDesignations();

           
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
           
            throw e; 
    }}
	
	
	@GetMapping("/{id}")
    public ResponseEntity<Response<Object>> getDesignationsId(@PathVariable Long id) {
    	
    	try {
        Response<Object> response = designationsService.getDesignationsById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    } catch (Exception e) {
    	throw e;
    }
	}
	
	
	
	@GetMapping("/department/{departmentsId}")
    public ResponseEntity<Response<Object>> getDesignationsByDepartmentsId(@PathVariable Long departmentsId) {
    	
    	try {
        Response<Object> response = designationsService.getDesignationsByDepartmentsId(departmentsId);
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    } catch (Exception e) {
    	throw e;
    }
	}
	
	
	@PostMapping("")
	public ResponseEntity<Response<?>> addDesignations(@RequestBody @Valid Designations designations, HttpServletRequest request) {
	    
		String clientIpAddress = getClientIp(request);
		designations.setIpaddress(clientIpAddress);
		
		
		try {
	      
	        Designations desig = designationsService.addDesignations(designations);

	        
	        Response<Designations> response = new Response<>("SUCCESS", "Designation added successfully.", desig);

	        
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    } catch (CustomException e) {
	        
	        ErrorResponse errorResponse = new ErrorResponse("FAILURE", e.getMessage(), e.getErrorCode());
	        Response<ErrorResponse> response = new Response<>("FAILURE", "Validation error", errorResponse);

	        
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	     
	        ErrorResponse errorResponse = new ErrorResponse("FAILURE", "An unexpected error occurred: " + e.getMessage(), "500");
	        Response<ErrorResponse> response = new Response<>("FAILURE", "Internal server error", errorResponse);
	  
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<Designations>> updateDesignation(
	        @PathVariable("id") Long id,
	        @RequestBody Designations updatedDesignation, HttpServletRequest request) {
		
		
		String clientIpAddress = getClientIp(request);
		updatedDesignation.setIpaddress(clientIpAddress);

	    
	    Departments department = departmentsService.getDepartmentById(updatedDesignation.getDepartments().getId());
	    if (department == null) {
	        
	        throw new CustomException("Department not found", "DEPARTMENT_NOT_FOUND");
	    }

	    updatedDesignation.setDepartments(department);
	    Designations updated = designationsService.updateDesignation(id, updatedDesignation);

	    if (updated != null) {
	        
	        Response<Designations> response = new Response<>("success", "Designation updated successfully", updated);
	        return ResponseEntity.ok(response);
	    } else {
	        Response<Designations> response = new Response<>("error", "Designation not found", null);
	        return ResponseEntity.notFound().build();
	    }
	}
	

	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDesignations(@PathVariable Long id) {
        try {
            String result = designationsService.deleteDesignations(id);
            
            return new ResponseEntity<>(new Response<>("SUCCESS", "Designations deleted successfully", result), HttpStatus.OK);
        } catch (CustomException e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), e.getErrorCode());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
           
            ErrorResponse errorResponse = new ErrorResponse("FAILED", "An unexpected error occurred: " + e.getMessage(), "500");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }}	



	
	
	
	
//Helper method to fetch client IP address
private String getClientIp(HttpServletRequest request) {
    String ipAddress = request.getHeader("X-Forwarded-For");
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getRemoteAddr();
    }
    return ipAddress;
}
}
	
	
	
	
	