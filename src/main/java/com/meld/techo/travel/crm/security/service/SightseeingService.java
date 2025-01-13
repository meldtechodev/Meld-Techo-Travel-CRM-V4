package com.meld.techo.travel.crm.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.models.Activities;
import com.meld.techo.travel.crm.models.Sightseeing;
import com.meld.techo.travel.crm.repository.SightseeingRepository;

@Service
public class SightseeingService {
	
	
	@Autowired
	private SightseeingRepository sightseeingRepository;
	
	
	public Sightseeing addSightseeing(Sightseeing sightseeing) {
		return sightseeingRepository.save(sightseeing);
	}
	
	
	public Optional<Sightseeing> getSightseeingById(Long id){
		return sightseeingRepository.findById(id);
	}
	
	
	public Page<Sightseeing> getSightseeing(int page , int size , String sortDirection){
		Sort sort = Sort.by(Sort.Order.asc("title"));
		
		if("desc".equalsIgnoreCase(sortDirection)) {
			sort = Sort.by(Sort.Order.by("title"));
		}
		
		
		PageRequest  pageable = PageRequest.of(page, size, sort);
		return sightseeingRepository.findAll(pageable);
	}

	
	
	public Sightseeing updateSightseeing(Sightseeing si) {
		return sightseeingRepository.save(si);
	}

	
	public void deleteById(long id) {
		sightseeingRepository.deleteById(id);
	}

	public Sightseeing findById(Long id) {
		return null;
		
	}
	
	
	public String deleteSightseeing(Long id) {
		Sightseeing existingSightseeing = sightseeingRepository.findById(id)
		            .orElseThrow(() -> new CustomException("Activities not found", "404"));
		existingSightseeing.setIsdelete(true); 

		    
		sightseeingRepository.save(existingSightseeing);

		    return "data deleted"; 
		}
	
	
}