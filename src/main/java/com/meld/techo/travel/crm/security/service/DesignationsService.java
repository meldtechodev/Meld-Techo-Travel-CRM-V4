package com.meld.techo.travel.crm.security.service;

import java.util.ArrayList;
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
import com.meld.techo.travel.crm.dto.DesignationsDTO;
import com.meld.techo.travel.crm.dto.Response;
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


//Paginated
   
   public Page<DesignationsDTO> getDesignations(int page, int size, String sortDirection){
	   Sort sort = Sort.by(Sort.Order.asc("designationName"));
	   
	   if("desc".equalsIgnoreCase(sortDirection)) {
		   sort = Sort.by(Sort.Order.desc("designationName"));
	   }
	   
	   
	   PageRequest pageable = PageRequest.of(page, size, sort);
	   Page<Designations> designation = designationsRepository.findAll(pageable);
	   
	   List<DesignationsDTO> filterdDesignations =  designation.stream()
			   .filter(designations -> !designations.isIsdelete())
			   .map(this::convertToDesignationsDTO3)
			   .collect(Collectors.toList());
	   
	   return new PageImpl(filterdDesignations, pageable, designation.getTotalElements());
	   }
   
   private DesignationsDTO convertToDesignationsDTO3(Designations designations) {
	   DesignationsDTO designationsDTO = new DesignationsDTO(
			   designations.getId(),
	  		   designations.getDesignationName(),
	  		   designations.getCreatedBy(),
	  		   designations.getModifiedBy(),
	  		   designations.getIpaddress(),
	  		   designations.isStatus(),
	  		   designations.isIsdelete(),
	  		   designations.getCreatedDate(),
	  		   designations.getModifiedDate(),
	  		   designations.getDepartments() != null ? designations.getDepartments().getId() : null,
	  		   designations.getDepartments() != null ? designations.getDepartments().getDepartmentName() : null
	     );
	  return designationsDTO;
	   
   }
   
   
   
   
   // GetAll
   public Response<Object> getAllDesignations(){
		 List<Designations> designationss = designationsRepository.findAll().stream()
				       .filter(designations -> !designations.isIsdelete())
				       .collect(Collectors.toList());
		   if(designationss.isEmpty()) {
			   
			   throw new CustomException("Not Getting Designations", "404");
	       }
			   List<DesignationsDTO> designationsDTOList = designationss.stream()
					   .map(this::convertToDesignationsDTO1).collect(Collectors.toList());
			   
			   return new Response<>("Successful", "Getting All Destination Successfully", designationsDTOList);
			   
		   }
 private DesignationsDTO convertToDesignationsDTO1(Designations designations) {
     return new DesignationsDTO(
  		   designations.getId(),
  		   designations.getDesignationName(),
  		   designations.getCreatedBy(),
  		   designations.getModifiedBy(),
  		   designations.getIpaddress(),
  		   designations.isStatus(),
  		   designations.isIsdelete(),
  		   designations.getCreatedDate(),
  		   designations.getModifiedDate(),
  		   designations.getDepartments() != null ? designations.getDepartments().getId() : null,
  		   designations.getDepartments() != null ? designations.getDepartments().getDepartmentName() : null
     );
 }
   
 
//Get Designations by DesignationsId
 
 public Response<Object> getDesignationsById(Long id) {
     Optional<Designations> designationss = designationsRepository.findById(id);
     if (designationss.isEmpty()) {
     	 throw new CustomException("Not Getting Designations", "404");
     }
     List<DesignationsDTO> designationsDTOList = designationss.stream().map(this::convertToDesignationsDTO1).collect(Collectors.toList());
     return new Response<>("Successful", "Designation fetched successfully", designationsDTOList);
 }
 
 private DesignationsDTO convertToDesignationsDTO2(Designations designations) {
     return new DesignationsDTO(
  		   designations.getId(),
  		   designations.getDesignationName(),
  		   designations.getCreatedBy(),
  		   designations.getModifiedBy(),
  		   designations.getIpaddress(),
  		   designations.isStatus(),
  		   designations.isIsdelete(),
  		   designations.getCreatedDate(),
  		   designations.getModifiedDate(),
  		   designations.getDepartments() != null ? designations.getDepartments().getId() : null,
  		   designations.getDepartments() != null ? designations.getDepartments().getDepartmentName() : null
     );
 }
 
 
 
//Get Designations By Departmentid
 public Response<Object> getDesignationsByDepartmentsId(Long departmentsId) {
     List<Designations> designationss = designationsRepository.findByDepartmentsId(departmentsId);
     if (designationss.isEmpty()) {
     	 throw new CustomException("Not Getting Designations", "404");
     }
     List<DesignationsDTO> designationsDTOList = designationss.stream().map(this::convertToDesignationsDTO1).collect(Collectors.toList());
     return new Response<>("Successful", "Designation fetched successfully By departmentsId", designationsDTOList);
 }
 
 private DesignationsDTO convertToDesignationsDTO(Designations designations) {
     return new DesignationsDTO(
  		   designations.getId(),
  		   designations.getDesignationName(),
  		   designations.getCreatedBy(),
  		   designations.getModifiedBy(),
  		   designations.getIpaddress(),
  		   designations.isStatus(),
  		   designations.isIsdelete(),
  		   designations.getCreatedDate(),
  		   designations.getModifiedDate(),
  		   designations.getDepartments() != null ? designations.getDepartments().getId() : null,
  		   designations.getDepartments() != null ? designations.getDepartments().getDepartmentName() : null
     );
 }
   
   
   //Create 
   public Designations addDesignations(Designations designations) {
	    if (designations.getDepartments() == null || designations.getDepartments().getId() == null) {
	        throw new CustomException("Department ID cannot be null.", "DEPARTMENT_ID_REQUIRED");
	    }

	    if (designationsRepository.existsByDesignationName(designations.getDesignationName())) {
	        throw new CustomException("Designation with name " + designations.getDesignationName() + " already exists.", "404");
	    }

	    Departments department = departmentsRepository.findById(designations.getDepartments().getId())
	            .orElseThrow(() -> new CustomException("Department with ID " + designations.getDepartments().getId() + " does not exist.", "DEPARTMENT_NOT_FOUND"));
	    designations.setDepartments(department);
	    
	    return designationsRepository.save(designations);
	}


   // Update code 
   public Designations updateDesignation(Long id, Designations updatedDesignation) {
       Designations existingDesignation = designationsRepository.findById(id).orElse(null);

       if (existingDesignation == null) {
           throw new CustomException("Designation not found", "404");
       }

       existingDesignation.setDesignationName(updatedDesignation.getDesignationName());
       existingDesignation.setDepartments(updatedDesignation.getDepartments());
       existingDesignation.setStatus(updatedDesignation.isStatus());
       existingDesignation.setModifiedBy(updatedDesignation.getModifiedBy());
       existingDesignation.setModifiedBy(updatedDesignation.getCreatedBy());
       existingDesignation.setIpaddress(updatedDesignation.getIpaddress());
       existingDesignation.setIsdelete(updatedDesignation.isIsdelete());
       return designationsRepository.save(existingDesignation);
   }
   
   
   
   // Delete
   public String deleteDesignations(Long id) {
	   Designations existingDesignations = designationsRepository.findById(id)
	            .orElseThrow(() -> new CustomException("Designations not found", "404"));
	    existingDesignations.setIsdelete(true); 

	    
	    designationsRepository.save(existingDesignations);

	    return "data deleted"; 
	}
   




   public Designations getDesignationById(Long id) {
	    Optional<Designations> designation = designationsRepository.findById(id);
	    return designation.orElse(null); // Return null if not found, or you can throw a custom exception
	}}

