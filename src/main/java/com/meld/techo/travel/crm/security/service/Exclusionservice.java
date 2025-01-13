package com.meld.techo.travel.crm.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.models.Exclusion;
import com.meld.techo.travel.crm.repository.ExclusionRepository;

@Service
public class Exclusionservice {
  
	@Autowired
	private ExclusionRepository exclusionrepository;
	
	public Exclusion createexclusionser(Exclusion exclusion)
	{
		           
		      
		Exclusion exclusionser= exclusionrepository.save(exclusion);
		
		       return exclusionser;
	}
	
	 
	public Page<Exclusion> getExclusion(int page , int size , String sortDirection){
		Sort sort = Sort.by(Sort.Order.asc("exclusionname"));
		
		if("desc".equalsIgnoreCase(sortDirection)) {
			sort = Sort.by(Sort.Order.desc("exclusionname"));
		}
		
		PageRequest pageable = PageRequest.of(page, size, sort);
		return exclusionrepository.findAll(pageable);
		}
	}