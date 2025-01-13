package com.meld.techo.travel.crm.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.models.Inclusion;
import com.meld.techo.travel.crm.repository.InclusionRepository;

@Service
public class InclusionService {

	@Autowired
	private InclusionRepository inclusionrepository;
	
	public Inclusion createInclusionser(Inclusion inclusion)
	{
		        
//		System.out.print("helloservice");
		  Inclusion saveinclusionser =     inclusionrepository.save(inclusion);
		   return saveinclusionser;
		
	}
	
	public Page<Inclusion> getInclusion(int page , int size , String sortDirection){
		Sort sort = Sort.by(Sort.Order.asc("inclusionname"));
		
		if("desc".equalsIgnoreCase(sortDirection)) {
			sort = Sort.by(Sort.Order.desc("inclusionname"));
		}
		
		PageRequest pageable = PageRequest.of(page, size, sort);
		return inclusionrepository.findAll(pageable);
		}}
		