package com.meld.techo.travel.crm.security.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Policy;
import com.meld.techo.travel.crm.repository.PolicyRepository;

@Service
public class PolicyService {
	
	
	@Autowired
	  private PolicyRepository policyRepository;

	  public Policy createpolicy(Policy policy)
	  {
		//  policy.setLastUpdated(LocalDateTime.now());
		  policy.setModifieddate(LocalDateTime.now());

		          Policy policyrepo =  policyRepository.save(policy);
		  
		return policyrepo;
		  
	  }
	  
	  public Optional<Policy> getpolicyById(Long id)
	  {
		  
		return policyRepository.findById(id);
		       
		     
	 }
	  
	  

	  
	  public List<Policy> getAllPolicies() {
	        List<Policy> policies = policyRepository.findAll();

	        // If no policies found, throw CustomException
	        if (policies.isEmpty()) {
	            throw new CustomException("No Policies found", "404");
	        }

	        // Filter out deleted policies
	        return policies.stream()
	                .filter(policy -> !policy.isIsdelete()) // Exclude deleted policies
	                .collect(Collectors.toList());
	    }

//	  public List<Policy> getallpolicyser()
//	  {
//		List<Policy>getpolicyre=      policyRepository.findAll();
//		
//		return getpolicyre;
//		  
//	  }
	  
	  
	  public ResponseEntity<Response<Page<Policy>>> getPolicy(int page, int size, String sortDirection) {
	        try {
	            // Define sorting logic
	            Sort sort = Sort.by(Sort.Order.asc("policyName"));
	            if ("desc".equalsIgnoreCase(sortDirection)) {
	                sort = Sort.by(Sort.Order.desc("policyName"));
	            }

	            // Define pagination with sorting
	            PageRequest pageable = PageRequest.of(page, size, sort);

	            // Fetch all policies with pagination
	            Page<Policy> policies = policyRepository.findAll(pageable);

	            // Filter out deleted policies
	            List<Policy> filteredPolicies = policies.getContent().stream()
	                    .filter(policy -> !policy.isIsdelete()) // Exclude deleted policies
	                    .collect(Collectors.toList());

	            // Handle the case when no policies are found
	            if (filteredPolicies.isEmpty()) {
	                throw new CustomException("No policies found", "404");
	            }

	            // Wrap the filtered policies into a new Page object
	            Page<Policy> filteredPage = new PageImpl<>(filteredPolicies, pageable, policies.getTotalElements());

	            // Wrap the response in the custom Response<T> object
	            Response<Page<Policy>> response = new Response<>(
	                    "success",
	                    "Policies fetched successfully",
	                    filteredPage
	            );

	            // Return the Response<T> wrapped in a ResponseEntity
	            return ResponseEntity.ok(response);
	        } catch (Exception ex) {
	            // Log exception details (optional)
	            ex.printStackTrace();

	            // Rethrow the exception to be handled by GlobalExceptionHandler
	            throw ex;
	        }
	    }
	  
	  
	  
	  
	  public Policy updatepolicy(Long id ,Policy policyDetails)
	  {
		  //  Optional<Policy> policy=  policyrepository.findById(id);
		  
		   return policyRepository.save(policyDetails);
		  
	  }
	  public void Deletebyid( Long id)
	  {
		 policyRepository.deleteById(id); 
	  }
}