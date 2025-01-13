package com.meld.techo.travel.crm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Policy;
import com.meld.techo.travel.crm.security.service.PolicyService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/policy")
public class PolicyController {
	
	@Autowired
	 
	private  PolicyService policyservice;
	 
	 @PostMapping("")
	public ResponseEntity<?> createPolicy(@Valid @RequestBody Policy policy,BindingResult bindingResult, HttpServletRequest request)
	{
		 
		 String clientIpAddress = getClientIp(request);
	        policy.setIpaddress(clientIpAddress);
		 
		  if(bindingResult.hasErrors())
   	   {
			  
			  
		        
   		   Map<String,String> errors = new HashMap<>();
   		   
   		    // Loop through each error and store the field and its message in the map
              bindingResult.getFieldErrors().forEach(error -> {
                  errors.put(error.getField(), error.getDefaultMessage());
              });
              
           // Return the map of errors with a BAD_REQUEST status
              return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
          }
        Policy policyser=  policyservice.createpolicy(policy);
        
		return new ResponseEntity<>("policy is created",HttpStatus.CREATED);
        
    }
	 
	 @GetMapping("/{id}")
	 
	public Optional<Policy> getallpolicybyid(@PathVariable Long id)
	{
		        Optional<Policy> policyserid=    policyservice.getpolicyById(id); 
		        
		     return policyserid;
		
	}
	 
	 
	 
//	 @GetMapping("/getallpolicy")
//	 public ResponseEntity<List<Policy>>getallpolicy()
//	 {
//		        List<Policy>getallpolicyser=  policyservice.getallpolicyser();
//		return ResponseEntity.ok(getallpolicyser);
//		 
//	 }
	 
	 
	 @GetMapping("/all/paginated")
	 public ResponseEntity<Response<Page<Policy>>> getPolicy(
	         @RequestParam(value = "page", defaultValue = "0") int page,
	         @RequestParam(value = "size", defaultValue = "10") int size,
	         @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection
	 ) {
	     // Call the service method and return the response
	     return policyservice.getPolicy(page, size, sortDirection);
	 }

	 
	 @GetMapping("/all")
	    public ResponseEntity<Response>getAllPolicies() {
	        try {
	            // Fetch all policies as DTOs
	            List<Policy> policies = policyservice.getAllPolicies();

	            // Check if list is empty
	            if (policies.isEmpty()) {
	                throw new CustomException("No policies found", "404");
	            }

	            // Wrap response in custom Response<T> object
	            Response<List> response = new Response<>(
	                "success",
	                "Policies fetched successfully",
	                policies
	            );

	            return ResponseEntity.ok(response);
	        } catch (CustomException e) {
	            // Handle known exceptions
	            Response<String> errorResponse = new Response<>(
	                "error",
	                e.getMessage(),
	                null
	            );
	            return ResponseEntity.status(404).body(errorResponse);
	        } catch (Exception e) {
	            // Handle unknown exceptions
	            Response<String> errorResponse = new Response<>(
	                "error",
	                "An unexpected error occurred",
	                null
	            );
	            return ResponseEntity.status(500).body(errorResponse);
	        }
	    }
	 
@PutMapping("/{id}")
	 
	 public ResponseEntity<Policy> updatepolicy( @PathVariable Long id,@RequestBody Policy policyDet, HttpServletRequest request)
	 {
	String clientIpAddress = getClientIp(request);
	policyDet.setIpaddress(clientIpAddress);
		 
		   if(policyDet !=null)
		   {
			   Policy policy=new Policy();
			   policy.setId(id);
			   policy.setPolicyName(policyDet.getPolicyName());
		        policy.setPolicyDescription(policyDet.getPolicyDescription());
		        policy.setCreatedby(policyDet.getCreatedby());
		        policy.setModifiedby(policyDet.getModifiedby());
		        policy.setIpaddress(policyDet.getIpaddress());
		        policy.setStatus(policyDet.isStatus());
		        policy.setIsdelete(policyDet.isIsdelete());
		        //policy.setCreatedby(policyDet.getCreatedby());
		        policy.setModifieddate(policyDet.getModifieddate());
		        policyservice.updatepolicy(id, policy);
		        
		          return ResponseEntity.ok(policy);
		   }
		  
		   else
		   {
			   return ResponseEntity.notFound().build();
		   }
		        
		          
		 
	 }
	 
	 @DeleteMapping("/{id}")
	 public void deletebyid(@PathVariable Long id)
	 {
		   policyservice.Deletebyid(id);
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
