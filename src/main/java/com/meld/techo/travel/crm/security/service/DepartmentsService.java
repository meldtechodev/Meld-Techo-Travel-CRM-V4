package com.meld.techo.travel.crm.security.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Departments;
import com.meld.techo.travel.crm.models.Designations;
import com.meld.techo.travel.crm.repository.DepartmentsRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class DepartmentsService {

@Autowired
private DepartmentsRepository departmentsRepository;


@Autowired
private HttpServletRequest request;


	// Get all with pageable
	public Page<Departments> getDepartments(int page, int size, String sortDirection) {
	    Sort sort = Sort.by(Sort.Order.asc("departmentName"));
	    if ("desc".equalsIgnoreCase(sortDirection)) {
	        sort = Sort.by(Sort.Order.desc("departmentName"));
	    }

	    PageRequest pageable = PageRequest.of(page, size, sort);
	    
	    Page<Departments> departmentPage = departmentsRepository.findAll(pageable);
	    List<Departments> filteredDepartments = departmentPage.getContent().stream()
	            .filter(departments -> !departments.isIsdelete())  
	            .collect(Collectors.toList());

	    return new PageImpl<>(filteredDepartments, pageable, departmentPage.getTotalElements());
	}
	
	
	// Get all
	public Response<Object> getAllDepartments() {
	    List<Departments> departmentsList = departmentsRepository.findAll().stream()
	            .filter(departments -> !departments.isIsdelete())  // Exclude deleted customers
	            .collect(Collectors.toList());

	    if (departmentsList.isEmpty()) {
	        throw new CustomException("No customers found", "404");
	    }

	    return new Response<>("Successful", "Getting All Departments Successfully", departmentsList);
	}


	//Get Departments by Departments Id
	public Response<Object> getDepartmentsById(Long id){
		Optional<Departments> departmentsOptional = departmentsRepository.findById(id);
		
		if(departmentsOptional.isEmpty()) {
			throw new CustomException(" Departments Not Founds", "404");
		}
		
		return new Response<>("Successful", "Getting Departments By Departmets Id", departmentsOptional);
	}
	
	
	// Create Service Code
	public Departments addDepartments(Departments departments) {
		
		if (departmentsRepository.existsByDepartmentName(departments.getDepartmentName())) {
            throw new IllegalArgumentException("Department with name " + departments.getDepartmentName() + " already exists.");
        }
		
     
     
		return departmentsRepository.save(departments);	
	}
	
	
	
	
	
	
	
	// Updated Service Code
	public Departments updateDepartments(Long id, Departments updatedDepartment) {
		Departments existingDepartments = departmentsRepository.findById(id).orElse(null);

	       if (existingDepartments == null) {
	           throw new CustomException("Departments not found", "404");
	       }

	       existingDepartments.setDepartmentName(updatedDepartment.getDepartmentName());
	       existingDepartments.setStatus(updatedDepartment.isStatus());
	       existingDepartments.setModifiedBy(updatedDepartment.getModifiedBy());
	       existingDepartments.setModifiedBy(updatedDepartment.getCreatedBy());
	       existingDepartments.setIpaddress(updatedDepartment.getIpaddress());
	       existingDepartments.setIsdelete(updatedDepartment.isIsdelete());
	       return departmentsRepository.save(existingDepartments);
	   }
	
	
	
	// Delete
	   public String deleteDepartments(Long id) {
		   Departments existingDepartments = departmentsRepository.findById(id)
		            .orElseThrow(() -> new CustomException("Departments not found", "404"));
		   existingDepartments.setIsdelete(true); 

		    
		   departmentsRepository.save(existingDepartments);

		    return "data deleted"; 
		}
	
	
	



	 public Departments getDepartmentById(Long id) {
	        return departmentsRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
	    }

}