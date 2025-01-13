package com.meld.techo.travel.crm.security.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.dto.StateDTO;
import com.meld.techo.travel.crm.models.State;
import com.meld.techo.travel.crm.repository.CountryRepository;
import com.meld.techo.travel.crm.repository.StateRepository;



@Service
public class StateService {
	
	  @Autowired
	  private StateRepository stateRepository;
	  
	  @Autowired
	  private CountryRepository countryRepository;
	  
	  
	  // Paginated Api
	  public Page<State> getState(int page, int size, String sortDirection) {
		    Sort sort = Sort.by(Sort.Order.asc("stateName"));
		    
		    if ("desc".equalsIgnoreCase(sortDirection)) {
		        sort = Sort.by(Sort.Order.desc("stateName"));
		    }

		    PageRequest pageable = PageRequest.of(page, size, sort);
		    return stateRepository.findByIsdeleteFalse(pageable);
		}
	  
	  
	 
	  // Create Api
	    public boolean existsByStateName(String stateName) {
	    	
	     return stateRepository.existsByStateName(stateName);
			
	    }

	    public State createState(State state) {
	        return stateRepository.save(state);
	    }

	    
	    // Updated Api
	    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/image/countryimage";
	    
	    private String timestamp;
	    public Response<Object> updateStateById(Long id, State stateDetails, MultipartFile[] files) throws IOException {
	        State existingState = stateRepository.findById(id).orElse(null);

	        // Check if the state exists
	        if (existingState == null) {
	            throw new CustomException("No State found", "404");
	        }

	        // Handle file upload and save image URLs
	        List<String> imageUrls = new ArrayList<>();
	        if (files != null) {
	            for (MultipartFile file : files) {
	                if (!isValidImage(file)) {
	                    throw new CustomException("File must be a JPEG or PNG image.", "400");
	                }

	                // Generate a unique filename for each file with timestamp and UUID
	                String uniqueFilename = generateUniqueFilename(file.getOriginalFilename());
	                Path filePath = Paths.get(uploadDirectory, uniqueFilename);
	                Files.write(filePath, file.getBytes());

	                // Add image URL to the list in the desired format
	                String imageUrl = "/image/" + uniqueFilename;
	                imageUrls.add(imageUrl);
	            }
	        }

	        // Update the existing state with new details
	        existingState.setStateName(stateDetails.getStateName());
	        existingState.setCode(stateDetails.getCode());
	        existingState.setSimage(imageUrls);
	        //existingState.setIpaddress(stateDetails.getIpaddress());
	        existingState.setStatus(stateDetails.isStatus());
	        existingState.setCreated_date(stateDetails.getCreated_date());
	        existingState.setModified_date(stateDetails.getModified_date());
	        existingState.setCreated_by(stateDetails.getCreated_by());
	        existingState.setModified_by(stateDetails.getModified_by());
	        existingState.setIsdelete(stateDetails.isIsdelete());
	        State updatedState = stateRepository.save(existingState);

	        return new Response<>("Successful", "State updated successfully", null);
	    }

	    private boolean isValidImage(MultipartFile file) {
	        String contentType = file.getContentType();
	        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
	    }

	    private String generateUniqueFilename(String originalFilename) {
	        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
	        String uniqueId = UUID.randomUUID().toString();
	        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
	        return timestamp + "_" + uniqueId + fileExtension;
	    }
	
	   

		
		
	    
	    public String deleteState(Long id) {
	   	 
			State existingState = stateRepository.findById(id)
		            .orElseThrow(() -> new CustomException("State not found", "404"));
			existingState.setIsdelete(true); 

		    
			stateRepository.save(existingState);

		    return "data deleted"; 
		}
	    
	    
		

		// For get all state
		 public Response<Object> getAllStates() {
		        List<State> states = stateRepository.findAll().stream()
		                .filter(state -> !state.isIsdelete())  // Exclude deleted states
		                .collect(Collectors.toList());

		        if (states.isEmpty()) {
		            throw new CustomException("Not Getting States", "404");
		        }
		 
		        List<StateDTO> stateDTOList = states.stream()
		                .map(this::convertToStateDTO)
		                .collect(Collectors.toList());

		        return new Response<>("Successful", "All states fetched successfully", stateDTOList);
		    }

		    
		    private StateDTO convertToStateDTO(State state) {
		        return new StateDTO(
		                state.getId(),
		                state.getStateName(),
		                state.getCode(),
		                state.isStatus(),
		                state.getCreated_date(),
		                state.getModified_date(),
		                state.getSimage(),  // Assuming simage is a list of strings
		                state.getCountry() != null ? state.getCountry().getId() : null,
		                state.getCountry() != null ? state.getCountry().getCountryName() : null
		        );
		    }
		

		    
		    // Gate state by countryid
		
		public Response<Object> getStatesByCountryId(Long countryId) {
	        List<State> states = stateRepository.findByCountryId(countryId);
	        if (states.isEmpty()) {
	        	 throw new CustomException("Not Getting State", "404");
	        }
	        List<StateDTO> stateDTOList = states.stream().map(this::convertToStateDTO).collect(Collectors.toList());
	        return new Response<>("Successful", "States fetched successfully", stateDTOList);
	    }

	    private StateDTO convertToStateDTO1(State state) {
	        return new StateDTO(
	                state.getId(),
	                state.getStateName(),
	                state.getCode(),
	                state.isStatus(),
	                state.getCreated_date(),
	                state.getModified_date(),
	                state.getSimage(),  // Assuming simage is a list of strings
	                state.getCountry() != null ? state.getCountry().getId() : null,
	                state.getCountry() != null ? state.getCountry().getCountryName() : null
	        );
	    }}

