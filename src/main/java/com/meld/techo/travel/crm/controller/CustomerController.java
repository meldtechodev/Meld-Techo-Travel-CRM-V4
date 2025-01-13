package com.meld.techo.travel.crm.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.CustomerDTO;
import com.meld.techo.travel.crm.dto.ErrorResponse;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Customer;
import com.meld.techo.travel.crm.models.User;
import com.meld.techo.travel.crm.repository.UserRepository;
import com.meld.techo.travel.crm.security.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;




@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/customer")
public class CustomerController {
	
	
	@Autowired
	private CustomerService customerService;
	
	
	@Autowired
    private UserRepository userRepository;
	
//	
//	@Autowired
//    private UserService userService;
	
	
	// Get All/paginated
		@GetMapping("/all/paginated")
		public ResponseEntity<?> getCustomer(
				@RequestParam(value = "page", defaultValue = "0") int page,
				@RequestParam(value = "size", defaultValue = "10") int size,
				@RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection){
			try {
				Page<CustomerDTO> customers = customerService.getCustomer(page, size, sortDirection);
				Response<Page<CustomerDTO>> response = new Response<>("SUCCESS", "Destinations fetched successfully", customers);
				return ResponseEntity.ok(response);
			}catch(Exception e) {
				ErrorResponse errorResponse = new ErrorResponse("FAILED", e.getMessage(), "500");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
			}
		}
		
		
		// Get All
		@GetMapping("/all")
		public ResponseEntity<Response<Object>> getAllCustomer(){
			try {
				Response<Object> response = customerService.getAllCustomer();
				return new ResponseEntity<>(response, HttpStatus.OK);
			} catch(Exception e) {
				throw e;
			}
		}
		
		 // Get By Customer By UserId 
		@GetMapping("/user/{userId}")
		public ResponseEntity<Response<Object>> getCustomerByUserId(@PathVariable Long userId){
			try {
				Response<Object> response = customerService.getCustomerByUserId(userId);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}catch(Exception e) {
				throw e;
			}
		}
		
		
		
		// Get Customer by Customer Id
		
		@GetMapping("/{id}")
		public ResponseEntity<Response<Object>> getCustomerId(@PathVariable Long id){
			try {
				Response<Object> response = customerService.getCustomerById(id);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}catch (Exception e) {
				
				throw e;
			}
		}
		
		
		


		 // Create Code 
		 @PostMapping("")
		    public ResponseEntity<?> createCustomer(@RequestBody Customer customer, HttpServletRequest request) {
		        try {
		            
		        	
		        	String clientIpAddress = getClientIp(request);
		            customer.setIpaddress(clientIpAddress);
		            return customerService.createCustomer(customer);

		        } catch (CustomException e) {
		            // Handle the custom exception and map it to ErrorResponse
		            ErrorResponse errorResponse = new ErrorResponse(
		                "FAILURE", 
		                e.getMessage(), 
		                e.getErrorCode()
		            );

		            Response<ErrorResponse> response = new Response<>(
		                "FAILURE", 
		                "Validation error", 
		                errorResponse
		            );

		            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

		        } catch (Exception e) {
		            // Handle unexpected exceptions
		            ErrorResponse errorResponse = new ErrorResponse(
		                "FAILURE", 
		                "An unexpected error occurred: " + e.getMessage(),
		                "500"
		            );

		            Response<ErrorResponse> response = new Response<>(
		                "FAILURE", 
		                "Internal server error", 
		                errorResponse
		            );

		            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
		 
		 
		 //Updated successfully
		 @PutMapping("/{id}")
		    public ResponseEntity<Response<Customer>> updateCustomer(
		            @PathVariable("id") Long id, 
		            @RequestBody Customer updatedCustomer, HttpServletRequest request) {
			 
			 
			 String clientIpAddress = getClientIp(request);
			 updatedCustomer.setIpaddress(clientIpAddress);
	            
		        // Retrieve the User entity using the UserRepository directly
		        User user = userRepository.findById(updatedCustomer.getUser().getId()).orElse(null);

		        // If the user is not found, return an error response
		        if (user == null) {
		            throw new CustomException("User Not Found", "404");
		        }

		        // Set the found User object to the updated customer
		        updatedCustomer.setUser(user);

		        // Proceed to update the customer entity
		        Customer updated = customerService.updateCustomer(id, updatedCustomer);

		        // Return response based on whether the customer was updated successfully
		        if (updated != null) {
		            Response<Customer> response = new Response<>("Successful", "Customer updated successfully", null);
		            return ResponseEntity.ok(response);
		        } else {
		            Response<Customer> response = new Response<>("error", "Customer not found", null);
		            return ResponseEntity.notFound().build();
		        }
		    }
		
		 
		 
		 
		 
		 
		 
//		 @PutMapping("/{id}")
//		 public ResponseEntity<Response<Customer>> updateCustomer(
//		         @PathVariable("id") Long id, 
//		         @RequestBody Customer updatedCustomer) {
//
//		     User users = userRepository.getUserById(updatedCustomer.getUser().getUserId());
//		     
//		     if (users == null) {
//		         throw new CustomException("User Not Found", "404");
//		     }
//		     
//		     updatedCustomer.setUser(users);
//		     
//		     Customer updated = customerService.updateCustomer(id, updatedCustomer);
//
//		     if (updated != null) {
//		         Response<Customer> response = new Response<>("Successful", "Customer updated successfully", null);
//		         return ResponseEntity.ok(response);
//		     } else {
//		         Response<Customer> response = new Response<>("error", "Customer not found", null);
//		         return ResponseEntity.notFound().build();
//		     }
//		 }
		 
		
		//Delete by id 
		@DeleteMapping("/{id}")
		public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
			try {
			String result = customerService.deleteCustomer(id);
			return new ResponseEntity<>(new Response<>(" Successful", "Delete Successfully", result), HttpStatus.OK);
			}catch(Exception e) {
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

		 
		 
		 
