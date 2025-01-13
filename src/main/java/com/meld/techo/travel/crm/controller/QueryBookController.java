package com.meld.techo.travel.crm.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.ErrorResponse;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.QueryBook;
import com.meld.techo.travel.crm.repository.UserRepository;
import com.meld.techo.travel.crm.security.service.CityService;
import com.meld.techo.travel.crm.security.service.CustomerService;
import com.meld.techo.travel.crm.security.service.QueryBookService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/query")
public class QueryBookController {
	
	
	
	@Autowired
	private QueryBookService querybookService;
	
	
	@Autowired
    private UserRepository userRepository;
	
//	@Autowired
//	private PkgRepository pkgRepository;
	
	@Autowired
	private CustomerService customerService;
	
	
	@Autowired
	private CityService cityService;
	
	
	
	@GetMapping("/all")
	public ResponseEntity<Response<Object>> getAllQueryBook(){
		try {
			Response<Object> response = querybookService.getAllQueryBook();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			
			throw e;
		}
		
	}
	
	
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<Response<Object>> getQueryBookByUserId(@PathVariable Long userId){
		try {
			Response<Object> response = querybookService.getQueryBookByUserId(userId);
					return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<Response<Object>> getQueryBookByCustomerId(@PathVariable Long customerId){
		try {
			Response<Object> response = querybookService.getQueryBookByCustomerId(customerId);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	@GetMapping("/city/{cityId}")
	public ResponseEntity<Response<Object>> getQueryBookByCityId(@PathVariable Long cityId){
		try {
		
		Response<Object> response = querybookService.getQueryBookByCityId(cityId);
		return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			throw e;
		}	
	}
	
	
	@PostMapping("")
    public ResponseEntity<Response<?>> addQueryBook(@RequestBody @Valid QueryBook query, HttpServletRequest request) {
        
		String clientIpAddress = getClientIp(request);
    	query.setIpaddress(clientIpAddress);
		
		try {
            // Call the service to add the QueryBook
            QueryBook queryBook = querybookService.addQueryBook(query);

            // Prepare and return the success response
            Response<QueryBook> response = new Response<>("Successful", "Created Successfully", queryBook);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (CustomException e) {
            // Handle validation or business exceptions
            ErrorResponse errorResponse = new ErrorResponse("FAILURE", e.getMessage(), e.getErrorCode());
            Response<ErrorResponse> response = new Response<>("FAILURE", "Validation error", errorResponse);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (IllegalArgumentException e) {
            // Handle invalid pack ID
            ErrorResponse errorResponse = new ErrorResponse("FAILURE", e.getMessage(), "400");
            Response<ErrorResponse> response = new Response<>("FAILURE", "Validation error", errorResponse);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // Handle unexpected errors
            ErrorResponse errorResponse = new ErrorResponse("FAILURE", "An unexpected error occurred: " + e.getMessage(), "500");
            Response<ErrorResponse> response = new Response<>("FAILURE", "Internal server error", errorResponse);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	
	@PutMapping("/{id}")
	public ResponseEntity<Response<QueryBook>> updateQueryBook(
	        @PathVariable("id") Long id,
	        @RequestBody QueryBook updatedQueryBook, HttpServletRequest request) {
		
		String clientIpAddress = getClientIp(request);
		updatedQueryBook.setIpaddress(clientIpAddress);

	    // Get existing QueryBook by id
	    QueryBook existingQueryBook = querybookService.findById(id);
	    if (existingQueryBook == null) {
	        throw new CustomException("QueryBook not found", "QUERY_NOT_FOUND");
	    }

	    // Update fields
	    existingQueryBook.setProposalId(updatedQueryBook.getProposalId());
	    existingQueryBook.setRequirementType(updatedQueryBook.getRequirementType());
	    existingQueryBook.setTravelDate(updatedQueryBook.getTravelDate());
	    existingQueryBook.setDays(updatedQueryBook.getDays());
	    existingQueryBook.setNights(updatedQueryBook.getNights());
	    existingQueryBook.setTotalTravellers(updatedQueryBook.getTotalTravellers());
	    existingQueryBook.setAdults(updatedQueryBook.getAdults());
	    existingQueryBook.setKids(updatedQueryBook.getKids());
	    existingQueryBook.setInfants(updatedQueryBook.getInfants());
	    existingQueryBook.setPackid(updatedQueryBook.getPackid());
	    existingQueryBook.setCity(updatedQueryBook.getCity());
	    existingQueryBook.setFromcityid(updatedQueryBook.getFromcityid());
	    existingQueryBook.setCustomer(updatedQueryBook.getCustomer());
	    existingQueryBook.setSalutation(updatedQueryBook.getSalutation());
	    existingQueryBook.setFname(updatedQueryBook.getFname());
	    existingQueryBook.setLname(updatedQueryBook.getLname());
	    existingQueryBook.setEmailId(updatedQueryBook.getEmailId());
	    existingQueryBook.setContactNo(updatedQueryBook.getContactNo());
	    existingQueryBook.setLeadSource(updatedQueryBook.getLeadSource());
	    existingQueryBook.setFoodPreferences(updatedQueryBook.getFoodPreferences());
	    existingQueryBook.setBasicCost(updatedQueryBook.getBasicCost());
	    existingQueryBook.setGst(updatedQueryBook.getGst());
	    existingQueryBook.setTotalCost(updatedQueryBook.getTotalCost());
	    existingQueryBook.setQueryType(updatedQueryBook.getQueryType());
	    existingQueryBook.setQueryCreatedFrom(updatedQueryBook.getQueryCreatedFrom());
	    existingQueryBook.setUserid(updatedQueryBook.getUserid());
	    existingQueryBook.setEmailStatus(updatedQueryBook.isEmailStatus());
	    existingQueryBook.setLeadStatus(updatedQueryBook.isLeadStatus());
	    existingQueryBook.setLastUpdated_Date(LocalDateTime.now());
	    existingQueryBook.setIpaddress(updatedQueryBook.getIpaddress());
	    existingQueryBook.setIsdelete(updatedQueryBook.isIsdelete());
	    existingQueryBook.setQuery_createby(updatedQueryBook.getQuery_createby());
	    existingQueryBook.setQuery_modifiedby(updatedQueryBook.getQuery_modifiedby());

	    // Save updated QueryBook
	    QueryBook updated = querybookService.updateQueryBook(id, existingQueryBook);

	    // Build response
	    Response<QueryBook> response = new Response<>("success", "Query updated successfully", updated);
	    return ResponseEntity.ok(response);
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
	
	

	
	
