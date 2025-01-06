package com.meld.techo.travel.crm.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.models.Role;
import com.meld.techo.travel.crm.repository.RoleRepository;


@Service
public class RoleService {
   @Autowired
   
   private RoleRepository roleRepository;
   
   public Role createRole(Role role)
   {
	   
	   if(roleRepository.findByRoleName(role.getRoleName()).isPresent())
	   {
           throw new RuntimeException("Role '" + role.getRoleName() + "' already exists.");

	   }
	   
	return roleRepository.save(role);
   }
	
	 public Page<Role> getRole(int page , int size , String sortDirection){
			Sort sort = Sort.by(Sort.Order.asc("roleName"));
			
			if("desc".equalsIgnoreCase(sortDirection)) {
				sort =  Sort.by(Sort.Order.desc("roleName"));
			}
			
			PageRequest  pageable = PageRequest.of(page, size, sort);
			return roleRepository.findAll(pageable);
			}
	   
	   
	}
	   
   
   
   
