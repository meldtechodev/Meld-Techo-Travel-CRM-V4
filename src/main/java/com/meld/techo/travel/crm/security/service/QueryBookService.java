package com.meld.techo.travel.crm.security.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.QueryBookDTO;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.City;
import com.meld.techo.travel.crm.models.Customer;
import com.meld.techo.travel.crm.models.QueryBook;
import com.meld.techo.travel.crm.repository.CityRepository;
import com.meld.techo.travel.crm.repository.CustomerRepository;
import com.meld.techo.travel.crm.repository.QueryBookRepository;
import com.meld.techo.travel.crm.repository.UserRepository;




@Service
public class QueryBookService {
	
	
	
//	@Autowired
//	private PkgRepository pkgRepository;
//	
	
	@Autowired
    private UserRepository userRepository;
	
	
	@Autowired
	private QueryBookRepository querybookRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	
	
	// Get All With DTO
		public Response<Object> getAllQueryBook(){
			List<QueryBook> queryBookList = querybookRepository.findAll().stream()
					      .filter(query -> !query.isIsdelete())
					      .collect(Collectors.toList());
			if(queryBookList.isEmpty()) {
				throw new CustomException("Not Found", "404");
			}
			
			List<QueryBookDTO> queryBookDTOList = queryBookList.stream()
					.map(this::convertToQueryBookDTO1).collect(Collectors.toList());
			return new Response<>("Successful", "Getting all Query successfully", queryBookDTOList);
		}
		
		
		private QueryBookDTO convertToQueryBookDTO1(QueryBook query) {
			
			QueryBookDTO queryBookDTO = new QueryBookDTO(
					 query.getId(),
			            query.getProposalId(),
			            query.getRequirementType(),
			            query.getTravelDate(),
			            query.getDays(),
			            query.getNights(),
			            query.getTotalTravellers(),
			            query.getAdults(),
			            query.getKids(),
			            query.getInfants(),
			            query.getSalutation(),
			            query.getFname(),
			            query.getLname(),
			            query.getEmailId(),
			            query.getContactNo(),
			            query.getLeadSource(),
			            query.getFoodPreferences(),
			            query.getBasicCost(),
			            query.getGst(),
			            query.getTotalCost(),
			            query.getQuery_Date(),
			            query.getQueryType(),
			            query.getQueryCreatedFrom(),
			            query.isEmailStatus(),
			            query.isLeadStatus(),
			            query.getLastUpdated_Date(),
			            query.getIpaddress(),
			            query.isIsdelete(),
			            
			            // Destination Information:
			            query.getCity() != null ? query.getCity().getId() : null,
			            query.getCity() != null ? query.getCity().getCityName() : null,
			            // Customer Information:
			            query.getCustomer() != null ? query.getCustomer().getId() : null,
			            query.getCustomer() != null ? query.getCustomer().getfName() : null,
			            query.getCustomer() != null ? query.getCustomer().getlName() : null,
			            // User Information:
			            query.getUserid() != null ? query.getUserid().getId() : null,
			            query.getUserid() != null ? query.getUserid().getName() : null,
			            query.getUserid() != null ? query.getUserid().getMname() : null,
			            query.getUserid() != null ? query.getUserid().getLname() : null,
			            query.getUserid() != null ? query.getUserid().getMobnumber() : null
			            		
			            		
			    );

			    return queryBookDTO;
			}
			
		
		
		public Response<Object> getQueryBookByUserId(Long userId){
			List<QueryBook> queryBookList = querybookRepository.findByUserId(userId);
			
			if(queryBookList.isEmpty()) {
				
				throw new CustomException("Query Not Found With this userId", "404");
				
			}
			
			
			List<QueryBookDTO> queryBookDTOList = queryBookList.stream()
					.map(this::convertToQueryBookDTO2).collect(Collectors.toList());
			return new Response<>("Successful", "Getting Successfully", queryBookDTOList);
		}
		
		private QueryBookDTO convertToQueryBookDTO2(QueryBook query) {
			QueryBookDTO queryBookDTO = new QueryBookDTO(
					 query.getId(),
			            query.getProposalId(),
			            query.getRequirementType(),
			            query.getTravelDate(),
			            query.getDays(),
			            query.getNights(),
			            query.getTotalTravellers(),
			            query.getAdults(),
			            query.getKids(),
			            query.getInfants(),
			            query.getSalutation(),
			            query.getFname(),
			            query.getLname(),
			            query.getEmailId(),
			            query.getContactNo(),
			            query.getLeadSource(),
			            query.getFoodPreferences(),
			            query.getBasicCost(),
			            query.getGst(),
			            query.getTotalCost(),
			            query.getQuery_Date(),
			            query.getQueryType(),
			            query.getQueryCreatedFrom(),
			            query.isEmailStatus(),
			            query.isLeadStatus(),
			            query.getLastUpdated_Date(),
			            query.getIpaddress(),
			            query.isIsdelete(),
			            
			            
			            query.getCity() != null ? query.getCity().getId() : null,
			            query.getCity() != null ? query.getCity().getCityName() : null,
			            
			            query.getCustomer() != null ? query.getCustomer().getId() : null,
			            query.getCustomer() != null ? query.getCustomer().getfName() : null,
			            query.getCustomer() != null ? query.getCustomer().getlName() : null,
			            
			            query.getUserid() != null ? query.getUserid().getId() : null,
			            query.getUserid() != null ? query.getUserid().getName() : null,
			            query.getUserid() != null ? query.getUserid().getMname() : null,
			            query.getUserid() != null ? query.getUserid().getLname() : null,
			            query.getUserid() != null ? query.getUserid().getMobnumber() : null
			            		
			            		
			    );

			    return queryBookDTO;
			}
		
		
		public Response<Object> getQueryBookByCustomerId(Long customerId){
			
			List<QueryBook> queryBookList = querybookRepository.findByCustomerId(customerId);
			if(queryBookList.isEmpty()) {
				throw new CustomException("Query not found with this customerId", "404");
			}
			
			List<QueryBookDTO> queryBookDTOList = queryBookList.stream()
					
					.map(this::convertToQueryBookDTO3).collect(Collectors.toList());
			return new Response<>("Successful", "Getting Successfully", queryBookDTOList);
		}
		
		private QueryBookDTO convertToQueryBookDTO3(QueryBook query) {
			
			QueryBookDTO  queryBookDTO = new QueryBookDTO(
					 query.getId(),
			            query.getProposalId(),
			            query.getRequirementType(),
			            query.getTravelDate(),
			            query.getDays(),
			            query.getNights(),
			            query.getTotalTravellers(),
			            query.getAdults(),
			            query.getKids(),
			            query.getInfants(),
			            query.getSalutation(),
			            query.getFname(),
			            query.getLname(),
			            query.getEmailId(),
			            query.getContactNo(),
			            query.getLeadSource(),
			            query.getFoodPreferences(),
			            query.getBasicCost(),
			            query.getGst(),
			            query.getTotalCost(),
			            query.getQuery_Date(),
			            query.getQueryType(),
			            query.getQueryCreatedFrom(),
			            query.isEmailStatus(),
			            query.isLeadStatus(),
			            query.getLastUpdated_Date(),
			            query.getIpaddress(),
			            query.isIsdelete(),
			            
			            
			            query.getCity() != null ? query.getCity().getId() : null,
			            query.getCity() != null ? query.getCity().getCityName() : null,
			            
			            query.getCustomer() != null ? query.getCustomer().getId() : null,
			            query.getCustomer() != null ? query.getCustomer().getfName() : null,
			            query.getCustomer() != null ? query.getCustomer().getlName() : null,
			            
			            query.getUserid() != null ? query.getUserid().getId() : null,
			            query.getUserid() != null ? query.getUserid().getName() : null,
			            query.getUserid() != null ? query.getUserid().getMname() : null,
			            query.getUserid() != null ? query.getUserid().getLname() : null,
			            query.getUserid() != null ? query.getUserid().getMobnumber() : null
			            		
			            		
			    );

			    return queryBookDTO;
			}
		
			
		public Response<Object> getQueryBookByCityId(Long cityId) {
			List<QueryBook> queryBookList = querybookRepository.findByCityId(cityId);
				if(queryBookList.isEmpty()) {
					throw new CustomException("Not Found Query with this cityId", "404");
				}
				
				List<QueryBookDTO> queryBookDTOList = queryBookList.stream()
						.map(this::convertToQueryBookDTO4).collect(Collectors.toList());
				return new Response<>("Successful", "Getting Successfully", queryBookDTOList);
			}
		
		private QueryBookDTO convertToQueryBookDTO4(QueryBook query) {
			
			QueryBookDTO queryBookDTO = new QueryBookDTO(
					 query.getId(),
			            query.getProposalId(),
			            query.getRequirementType(),
			            query.getTravelDate(),
			            query.getDays(),
			            query.getNights(),
			            query.getTotalTravellers(),
			            query.getAdults(),
			            query.getKids(),
			            query.getInfants(),
			            query.getSalutation(),
			            query.getFname(),
			            query.getLname(),
			            query.getEmailId(),
			            query.getContactNo(),
			            query.getLeadSource(),
			            query.getFoodPreferences(),
			            query.getBasicCost(),
			            query.getGst(),
			            query.getTotalCost(),
			            query.getQuery_Date(),
			            query.getQueryType(),
			            query.getQueryCreatedFrom(),
			            query.isEmailStatus(),
			            query.isLeadStatus(),
			            query.getLastUpdated_Date(),
			            query.getIpaddress(),
			            query.isIsdelete(),
			            
			            
			            query.getCity() != null ? query.getCity().getId() : null,
			            query.getCity() != null ? query.getCity().getCityName() : null,
			            
			            query.getCustomer() != null ? query.getCustomer().getId() : null,
			            query.getCustomer() != null ? query.getCustomer().getfName() : null,
			            query.getCustomer() != null ? query.getCustomer().getlName() : null,
			            
			            query.getUserid() != null ? query.getUserid().getId() : null,
			            query.getUserid() != null ? query.getUserid().getName() : null,
			            query.getUserid() != null ? query.getUserid().getMname() : null,
			            query.getUserid() != null ? query.getUserid().getLname() : null,
			            query.getUserid() != null ? query.getUserid().getMobnumber() : null
			            		
			            		
			    );

			    return queryBookDTO;
			}
		
		
		public QueryBook addQueryBook(QueryBook query) {
	        // Validate pack ID
//	        if (query.getPackid() == null || !pkgRepository.existsById(query.getPackid())) {
//	            throw new IllegalArgumentException("Pack ID does not exist");
//	        }

	         //Validate destination ID
	        if (query.getCity() == null || query.getCity().getId() == null) {
	            throw new CustomException("City ID cannot be null.", "City_ID_REQUIRED");
	        }

	        // Validate user ID
//	        if (query.getUserid() == null || query.getUserid().getUserId() == null) {
//	            throw new CustomException("User ID cannot be null.", "USER_ID_REQUIRED");
//	        }

	        // Validate customer ID
	        if (query.getCustomer() == null || query.getCustomer().getId() == -1) {
	            throw new CustomException("Customer ID cannot be null.", "Customer_ID_REQUIRED");
	        }

	        // Find destination by ID
	        City city = cityRepository.findById(query.getCity().getId())
	                .orElseThrow(() -> new CustomException("City with ID " + query.getCity().getId() + " does not exist.", "CITY_NOT_FOUND"));
	        query.setCity(city);

	        // Find user by ID
//	        User user = userRepository.findById(query.getUserid().getUserId())
//	                .orElseThrow(() -> new CustomException("User with ID " + query.getUserid().getUserId() + " does not exist.", "USER_NOT_FOUND"));
//	        query.setUserid(user);

	        // Find customer by ID
	        Customer customer = customerRepository.findById(query.getCustomer().getId())
	                .orElseThrow(() -> new CustomException("Customer with ID " + query.getCustomer().getId() + " does not exist.", "USER_NOT_FOUND"));
	        query.setCustomer(customer);

	        // Save the QueryBook and return it
	        return querybookRepository.save(query);
	    }

		
		
		public QueryBook findById(Long id) {
		    return querybookRepository.findById(id)
		            .orElseThrow(() -> new CustomException("Query not found", "QUERY_NOT_FOUND"));
		}

		public QueryBook updateQueryBook(Long id, QueryBook updatedQueryBook) {
		    // Get existing QueryBook from the database
		    QueryBook existingQueryBook = findById(id);

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

		    // Save the updated QueryBook entity to the repository
		    return querybookRepository.save(existingQueryBook);
		}}
	