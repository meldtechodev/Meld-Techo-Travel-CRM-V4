package com.meld.techo.travel.crm.security.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.models.Departments;
import com.meld.techo.travel.crm.repository.DepartmentsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DepartmentsService {

@Autowired
	private DepartmentsRepository departmentsRepository;

	public Optional<Departments> getDepartmentsById(Long id){
		return departmentsRepository.findById(id);
	}

	public List<Departments> getAllDepartments() {
        List<Departments> depart = departmentsRepository.findAll();

        return depart.stream()
                        .filter(departments -> !departments.isIsdelete())  // Keep countries where isDelete is false
                        .collect(Collectors.toList());
    }
 
	
	public Page<Departments> getDepartments(int page, int size, String sortDirection) {
        // Set default sorting direction to ascending
        Sort sort = Sort.by(Sort.Order.asc("departmentName"));
        // Change sorting direction based on the user input
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(Sort.Order.desc("departmentName"));
        }
        // Create a Pageable object with page, size, and sort
        PageRequest pageable = PageRequest.of(page, size, sort);
        // Return the paginated and sorted result
        return departmentsRepository.findAll(pageable);
    }


	public Departments addDepartments(Departments departments) {
        // Check if department name already exists
        if (departmentsRepository.existsByDepartmentName(departments.getDepartmentName())) {
            throw new IllegalArgumentException("Department with name " + departments.getDepartmentName() + " already exists.");
        }
        // Save the department if it doesn't exist
        return departmentsRepository.save(departments);
    }



	 public Departments getDepartmentById(Long id) {
	        return departmentsRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
	    }

	public void deleteById(Long id) {
	    // Check if the company exists
	    Optional<Departments> existingDepartmentsOptional = departmentsRepository.findById(id);
	    if (existingDepartmentsOptional.isPresent()) {
	        // Delete the company if it exists
	    	departmentsRepository.deleteById(id);
	    } else {
	        // Handle case where company is not found
	        throw new EntityNotFoundException("Departments with ID " + id + " not found.");
	    }
	}

	public Departments updateDepartments(Departments de) {
		return departmentsRepository.save(de);
	}



	public String deleteDepartments(Long id) {
        // Check if the country exists before attempting to delete
		Departments existingDepartments = departmentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departments not found"));

		existingDepartments.setId(id);
		existingDepartments.setDepartmentName(existingDepartments.getDepartmentName());
		existingDepartments.setCreatedBy(existingDepartments.getCreatedBy());
		existingDepartments.setModifiedBy(existingDepartments.getModifiedBy());
		existingDepartments.setIpaddress(existingDepartments.getIpaddress());
		existingDepartments.setStatus(existingDepartments.isStatus());
		existingDepartments.setIsdelete(true);

		departmentsRepository.save(existingDepartments);
        return "data deleted";
    }
 
 
	




}