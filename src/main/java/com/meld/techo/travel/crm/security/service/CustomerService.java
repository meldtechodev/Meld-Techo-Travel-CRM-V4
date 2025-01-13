package com.meld.techo.travel.crm.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.CustomerDTO;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Customer;
import com.meld.techo.travel.crm.models.User;
import com.meld.techo.travel.crm.repository.CustomerRepository;
import com.meld.techo.travel.crm.repository.UserRepository;



@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Autowired
    private UserRepository userRepository;
	
	
	public Page<CustomerDTO> getCustomer(int page, int size, String sortDirection){
		Sort sort = Sort.by(Sort.Order.asc("fName"));
		
		 if("desc".equalsIgnoreCase(sortDirection)) {
		sort = Sort.by(Sort.Order.desc("fName"));
		 }
		 
		 PageRequest pageable = PageRequest.of(page, size, sort);
		 Page<Customer> customers = customerRepository.findAll(pageable);
		 
		 List<CustomerDTO> filterdCustomer = customers.stream()
				      .filter(customer -> !customer.isIsdelete())
				      .map(this::convertToCustomerDTO1)
				      .collect(Collectors.toList());
		 
		 return new PageImpl(filterdCustomer, pageable, customers.getTotalElements());
	}
	
	private CustomerDTO  convertToCustomerDTO1(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO(
				customer.getId(),
				customer.getSalutation(),
				customer.getfName(),
				customer.getlName(),
				customer.getEmailId(),
				customer.getContactNo(),
				customer.getMarritalStatus(),
				customer.getCustomerType(),
				customer.getLeadSource(),
				customer.getAdharno(),
				customer.getPassportId(),
				customer.getCreatedby(),
				customer.getModifiedby(),
				customer.getIpaddress(),
				customer.isStatus(),
				customer.isIsdelete(),
				customer.getCreatedDate(),
				customer.getModifiedDate(),
				customer.getUser() != null ? customer.getUser().getId() : null,
				customer.getUser() != null ? customer.getUser().getName() : null,
				customer.getUser() != null ? customer.getUser().getMname() : null,
				customer.getUser() != null ? customer.getUser().getLname() : null
						);
		
		return customerDTO;
		
	}
	
	
	
	// OR This One
		public Response<Object> getAllCustomer(){
			List<Customer> customerList = customerRepository.findAll().stream()
					                  .filter(customer -> !customer.isIsdelete())
					                  .collect(Collectors.toList());
			if(customerList.isEmpty()) {
				throw new CustomException("Not Getting Customer", "404");
			}
			
			List<CustomerDTO> customerDTOList = customerList.stream()
					.map(this::convertToCustomerDTO2).collect(Collectors.toList());
			return new Response<>("Successful", "Getting All Customer Successfully", customerDTOList);
		}
		
		
		private CustomerDTO convertToCustomerDTO2(Customer customer) {
			CustomerDTO customerDTO = new CustomerDTO(
					customer.getId(),
					customer.getSalutation(),
					customer.getfName(),
					customer.getlName(),
					customer.getEmailId(),
					customer.getContactNo(),
					customer.getMarritalStatus(),
					customer.getCustomerType(),
					customer.getLeadSource(),
					customer.getAdharno(),
					customer.getPassportId(),
					customer.getCreatedby(),
					customer.getModifiedby(),
					customer.getIpaddress(),
					customer.isStatus(),
					customer.isIsdelete(),
					customer.getCreatedDate(),
					customer.getModifiedDate(),
					customer.getUser() != null ? customer.getUser().getId() : null,
					customer.getUser() != null ? customer.getUser().getName() : null,
					customer.getUser() != null ? customer.getUser().getMname() : null,
					customer.getUser() != null ? customer.getUser().getLname() : null
							);
			return customerDTO;
		}
		
		
		
		// Get Customer  by UserId
		public Response<Object> getCustomerByUserId(Long userId){
			List<Customer> customerList = customerRepository.findByUserId(userId);
			
			if(customerList.isEmpty()) {
				throw new CustomException("Not Found", "404");
			}
			
			List<CustomerDTO> customerDTOList = customerList.stream()
					     .map(this::convertToCustomerDTO3)
					     .collect(Collectors.toList());
			return new Response<>("Successful", "Getting Customer Successfully by UserId", customerDTOList);
			}
		
		
		private CustomerDTO convertToCustomerDTO3(Customer customer) {
			CustomerDTO customerDTO = new CustomerDTO(
					customer.getId(),
					customer.getSalutation(),
					customer.getfName(),
					customer.getlName(),
					customer.getEmailId(),
					customer.getContactNo(),
					customer.getMarritalStatus(),
					customer.getCustomerType(),
					customer.getLeadSource(),
					customer.getAdharno(),
					customer.getPassportId(),
					customer.getCreatedby(),
					customer.getModifiedby(),
					customer.getIpaddress(),
					customer.isStatus(),
					customer.isIsdelete(),
					customer.getCreatedDate(),
					customer.getModifiedDate(),
					customer.getUser() != null ? customer.getUser().getId() : null,
					customer.getUser() != null ? customer.getUser().getName() : null,
					customer.getUser() != null ? customer.getUser().getMname() : null,
					customer.getUser() != null ? customer.getUser().getLname() : null
							
					);
			return customerDTO;
			
		}
		
		
		
		// Get Customer  by Customer Id
		public Response<Object> getCustomerById(Long id){
			Optional<Customer> customerOptional = customerRepository.findById(id);
			if(customerOptional.isEmpty()) {
				
				throw new CustomException("Not Found", "404");
			}
			
			List<CustomerDTO> customerDTOList = customerOptional.stream()
					.map(this::convertToCustomerDTO4).collect(Collectors.toList());
			return new Response<>("Successful", "Getting Customer Successfully", customerDTOList);
		}
		
		private CustomerDTO convertToCustomerDTO4(Customer customer) {
			CustomerDTO customerDTO = new CustomerDTO(
					customer.getId(),
					customer.getSalutation(),
					customer.getfName(),
					customer.getlName(),
					customer.getEmailId(),
					customer.getContactNo(),
					customer.getMarritalStatus(),
					customer.getCustomerType(),
					customer.getLeadSource(),
					customer.getAdharno(),
					customer.getPassportId(),
					customer.getCreatedby(),
					customer.getModifiedby(),
					customer.getIpaddress(),
					customer.isStatus(),
					customer.isIsdelete(),
					customer.getCreatedDate(),
					customer.getModifiedDate(),
					customer.getUser() != null ? customer.getUser().getId() : null,
					customer.getUser() != null ? customer.getUser().getName() : null,
					customer.getUser() != null ? customer.getUser().getMname() : null,
					customer.getUser() != null ? customer.getUser().getLname() : null
							);
			return customerDTO;
		}
		
		
		// Create Service code
		public ResponseEntity<?> createCustomer(Customer customer) {

	        if (customerRepository.existsByEmailId(customer.getEmailId())) {
	            throw new CustomException("EmailId already exists.", "400");
	        }
	        
	        
	        if (customer.getUser() == null || customer.getUser().getId() == null) {
		        throw new CustomException("User ID cannot be null.", "User_ID_REQUIRED");
		    }

	        if (customerRepository.existsByContactNo(customer.getContactNo())) {
	            Customer existingCustomer = customerRepository.findByContactNo(customer.getContactNo());

	            // Create the custom response for validation error with the existing customer details
	            Map<String, Object> errorData = new HashMap<>();
	            errorData.put("status", "FAILURE");
	            errorData.put("message", "Customer already exists with this ContactNo.");
	            errorData.put("errorCode", "409");
	            errorData.put("customer", existingCustomer);

	            Map<String, Object> response = new HashMap<>();
	            response.put("status", "FAILURE");
	            response.put("message", "Validation error");
	            response.put("data", errorData);

	            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	        }

	        if (customer.getAdharno() != null && !customer.getAdharno().isEmpty() && customerRepository.existsByAdharno(customer.getAdharno())) {
	            throw new CustomException("Aadhar Number already exists.", "AADHAR_EXISTS");
	        }

	       
	        if (customer.getPassportId() != null && !customer.getPassportId().isEmpty() && customerRepository.existsByPassportId(customer.getPassportId())) {
	            throw new CustomException("Passport ID already exists.", "PASSPORT_EXISTS");
	        }
	        
	        
	        User user = userRepository.findById(customer.getUser().getId())
		            .orElseThrow(() -> new CustomException("User with ID " + customer.getUser().getId() + " does not exist.", "400"));
		    customer.setUser(user);

	        Customer savedCustomer = customerRepository.save(customer);

	        Map<String, Object> successResponse = new HashMap<>();
	        successResponse.put("status", "Successful");
	        successResponse.put("message", "Created successfully");
	        successResponse.put("customer", savedCustomer);

	        return new ResponseEntity<>(successResponse, HttpStatus.CREATED); // HTTP 201 Created
	    }
		
		
		
		// Update Api
		public Customer updateCustomer(Long id, Customer updatedCustomer) {
		       Customer existingCustomer = customerRepository.findById(id).orElse(null);

		       if (existingCustomer == null) {
		           throw new CustomException("Customer not found", "404");
		       }
		       
		       existingCustomer.setId(id);
		       existingCustomer.setSalutation(updatedCustomer.getSalutation());
		       existingCustomer.setfName(updatedCustomer.getfName());
		       existingCustomer.setlName(updatedCustomer.getlName());
		       existingCustomer.setEmailId(updatedCustomer.getEmailId());
		       existingCustomer.setContactNo(updatedCustomer.getContactNo());
		       existingCustomer.setMarritalStatus(updatedCustomer.getMarritalStatus());
		       existingCustomer.setCustomerType(updatedCustomer.getCustomerType());
		       existingCustomer.setLeadSource(updatedCustomer.getLeadSource());
		       existingCustomer.setAdharno(updatedCustomer.getAdharno());
		       existingCustomer.setPassportId(updatedCustomer.getPassportId());
		       existingCustomer.setCreatedby(updatedCustomer.getCreatedby());
		       existingCustomer.setModifiedby(updatedCustomer.getModifiedby());
		       existingCustomer.setCreatedDate(updatedCustomer.getCreatedDate());
		       existingCustomer.setIpaddress(updatedCustomer.getIpaddress());
		       existingCustomer.setModifiedDate(updatedCustomer.getModifiedDate());
		       existingCustomer.setStatus(updatedCustomer.isStatus());
		       existingCustomer.setIsdelete(updatedCustomer.isIsdelete());
		       return customerRepository.save(existingCustomer);
		   }
		
		
		
		
		// Delete Api
		public String deleteCustomer(Long id) {
			Customer existingCustomer = customerRepository.findById(id)
					.orElseThrow(() -> new CustomException("Not Found", "404"));
			existingCustomer.setIsdelete(true);
			
			customerRepository.save(existingCustomer);
			return "Data Deleted";
			
		}
}
	