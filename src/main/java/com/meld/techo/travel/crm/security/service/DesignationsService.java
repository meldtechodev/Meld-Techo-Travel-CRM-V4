package com.meld.techo.travel.crm.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.DesignationsDTO;
import com.meld.techo.travel.crm.models.Departments;
import com.meld.techo.travel.crm.models.Designations;
import com.meld.techo.travel.crm.repository.DepartmentsRepository;
import com.meld.techo.travel.crm.repository.DesignationsRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
@Service
public class DesignationsService {

	@Autowired
	private DesignationsRepository designationsRepository;
   @Autowired
	private DepartmentsRepository departmentsRepository;
   @Autowired
   private DepartmentsService departmentsService;


   public List<DesignationsDTO> getAllDesignations() {
       List<Designations> designations = designationsRepository.findAll();
       return designations.stream()
                          .map(Designations::toDTO)
                          .collect(Collectors.toList());
   }
 
   
   public DesignationsDTO getDesignationById(Long id) {
       Optional<Designations> designationOptional = designationsRepository.findById(id);
       return designationOptional.map(Designations::toDTO).orElse(null);
   }
   public List<DesignationsDTO> getDesignationsByDepartmentsId(Long departmentsId) {
	    List<Designations> designations = designationsRepository.findByDepartmentsId(departmentsId);
	    return designations.stream()
	                       .map(Designations::toDTO)
	                       .collect(Collectors.toList());
	}

	public Designations addDesignations(Designations designations) {
        // Check if department name already exists
        if (designationsRepository.existsByDesignationName(designations.getDesignationName())) {
            throw new IllegalArgumentException("Department with name " + designations.getDesignationName() + " already exists.");
        }
        // Save the department if it doesn't exist
        return designationsRepository.save(designations);
    }

	
 
	



	public Page<Designations> getDesignations(int page, int size, String sortDirection){
		Sort sort = Sort.by(Sort.Order.asc("designationName"));
		if("desc".equalsIgnoreCase(sortDirection)) {
			sort = Sort.by(Sort.Order.desc("designationName"));
		}
		PageRequest pageable = PageRequest.of(page, size, sort);
		return designationsRepository.findAll(pageable);
	}

	public Optional<Designations> getDesignationsById(Long id){
		return designationsRepository.findById(id);
	}



	
   
    @Transactional
    public Designations updateDesignation(Long id, Designations updatedDesignation) {
        Optional<Designations> existingDesignationOpt = designationsRepository.findById(id);
        if (existingDesignationOpt.isPresent()) {
            Designations existingDesignation = existingDesignationOpt.get();
            // Ensure the department exists
            Departments department = departmentsService.getDepartmentById(updatedDesignation.getDepartments().getId());
            if (department == null) {
                // Department doesn't exist
                throw new EntityNotFoundException("Department with ID " + updatedDesignation.getDepartments().getId() + " not found.");
            }
            // Set the valid department
            existingDesignation.setDepartments(department);
            existingDesignation.setDesignationName(updatedDesignation.getDesignationName());
            existingDesignation.setStatus(updatedDesignation.isStatus());
            existingDesignation.setModifiedBy(updatedDesignation.getModifiedBy());
            existingDesignation.setIpaddress(updatedDesignation.getIpaddress());
            existingDesignation.setIsdelete(updatedDesignation.isIsdelete());
            // Save the updated entity
            return designationsRepository.save(existingDesignation);
        }
        return null;
    }

    public String deleteDesignations(Long id) {
    	Designations existingDesignations = designationsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Designations not found"));

 
	 
    	existingDesignations.setDesignationName(existingDesignations.getDesignationName());
    	existingDesignations.setStatus(existingDesignations.isStatus());
    	existingDesignations.setModifiedBy(existingDesignations.getModifiedBy());
    	existingDesignations.setIpaddress(existingDesignations.getIpaddress());
    	existingDesignations.setIsdelete(true);

 
    	designationsRepository.save(existingDesignations);
    return "data deleted";
}}


