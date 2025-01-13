package com.meld.techo.travel.crm.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.models.Activities;
import com.meld.techo.travel.crm.models.Company;
import com.meld.techo.travel.crm.repository.ActivitiesRepository;

@Service
public class ActivitiesService {
	
	
	@Autowired
	private ActivitiesRepository activitiesRepository;
	
	
	public Optional<Activities> getActivitiesById(Long id){
		return activitiesRepository.findById(id);
	}
	
	
public Page<Activities> getActivities(int page, int size, String sortDirection){
		
		Sort sort = Sort.by(Sort.Order.asc("title"));
		
		if("desc".equalsIgnoreCase(sortDirection)) {
			sort = Sort.by(Sort.Order.desc("title"));
		}
		
		PageRequest pageable = PageRequest.of(page, size, sort);
		
		return activitiesRepository.findAll(pageable);
			
			
			
		}
	
	
	public Activities addActivities(Activities activities) {
		return activitiesRepository.save(activities);
	}
	
	
	public boolean existsByTitle(String title) {
		return activitiesRepository.existsByTitle(title);
	}
	
	
	public Activities updateActivities(Activities ai) {
		return activitiesRepository.save(ai);
	}
	
	
	
	
	public String deleteActivities(Long id) {
		Activities existingActivities = activitiesRepository.findById(id)
		            .orElseThrow(() -> new CustomException("Activities not found", "404"));
		existingActivities.setIsdelete(true); 

		    
		activitiesRepository.save(existingActivities);

		    return "data deleted"; 
		}

}