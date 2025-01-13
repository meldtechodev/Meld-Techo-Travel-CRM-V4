package com.meld.techo.travel.crm.security.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.DesignationModulesDTO;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.DesignationModules;
import com.meld.techo.travel.crm.models.Designations;
import com.meld.techo.travel.crm.models.Modules;
import com.meld.techo.travel.crm.repository.DesignationModulesRepository;
import com.meld.techo.travel.crm.repository.DesignationsRepository;
import com.meld.techo.travel.crm.repository.ModulesRepository;

import jakarta.persistence.EntityNotFoundException;
 
@Service
public class DesignationModulesService {
	
	
	@Autowired
	private DesignationModulesRepository designationModulesRepository;
	
	
	@Autowired
	private DesignationsRepository designationsRepository;
	
	
	@Autowired
	private ModulesRepository modulesRepository;
	
	
	
	//Get All DesignationModules
	public Response<Object> getAllDesignationModules(){
		List<DesignationModules> filterddesignationModule = designationModulesRepository.findAll().stream()
				       .filter(designationModules -> !designationModules.isIsdelete())
				       .collect(Collectors.toList());
		if(filterddesignationModule.isEmpty()) {
			throw new CustomException("DesignationModules Not Found", "404");
		}
		List<DesignationModulesDTO> designationModulesList = filterddesignationModule.stream()
				   .map(this::convertToDesignationModulesDTO).collect(Collectors.toList());
		return new Response<>("Successful", "Getting DesignationModules Successfully", designationModulesList);
	}
	
	private DesignationModulesDTO convertToDesignationModulesDTO(DesignationModules designationModules) {
		
		DesignationModulesDTO designationModulesDTO = new DesignationModulesDTO(
				designationModules.getId(),
				designationModules.getCreatedBy(),
				designationModules.getIpaddress(),
				designationModules.isStatus(),
				designationModules.getDesignations() != null ? designationModules.getDesignations().getId() : null,
				designationModules.getDesignations() != null ? designationModules.getDesignations().getDesignationName() : null,
				designationModules.getModules() != null ? designationModules.getModules().getId() : null,
				designationModules.getModules() != null ? designationModules.getModules().getModuleName() : null
						);
		return designationModulesDTO;
	}
	
	
	// Get DesignationModules by DesignationModules Id
	public Response<Object> getDesignationModulesById(Long id){
		Optional<DesignationModules> designationModulesOptional = designationModulesRepository.findById(id);
		if(designationModulesOptional.isEmpty()) {
			throw new CustomException("Not Found", "404");
		}
		
		List<DesignationModulesDTO> designationModulesDTOList = designationModulesOptional.stream()
				.map(this::convertToDesignationModulesDTO).collect(Collectors.toList());
		return new Response<>("Successful", "Getting DesignationModules Successfully", designationModulesDTOList);
		
	}
	
	private DesignationModulesDTO convertToDesignationModulesDTO1(DesignationModules designationModules) {
		
		DesignationModulesDTO designationModulesDTO =  new DesignationModulesDTO(
				designationModules.getId(),
				designationModules.getCreatedBy(),
				designationModules.getIpaddress(),
				designationModules.isStatus(),
				designationModules.getDesignations() != null ? designationModules.getDesignations().getId() : null,
				designationModules.getDesignations() != null ? designationModules.getDesignations().getDesignationName() : null,
				designationModules.getModules() != null ? designationModules.getModules().getId() : null,
				designationModules.getModules()	!= null ? designationModules.getModules().getModuleName() : null
						);
		return designationModulesDTO;
	}
	
	
	// Get DesignationModules by Designations Id
	public Response<Object> getDesignationModulesByDesignationsId(Long designationsId){
		List<DesignationModules> designationModulesList = designationModulesRepository.findByDesignationsId(designationsId);
		if(designationModulesList.isEmpty()) {
			throw new CustomException(" Not Found", "404");
		}
		
		List<DesignationModulesDTO> designationModulesDTOList = designationModulesList.stream()
				.map(this::convertToDesignationModulesDTO).collect(Collectors.toList());
		return new Response<>("Successful", "Getting DesignationModules Successfully", designationModulesDTOList);
	}
	
	private DesignationModulesDTO convertToDesignationModulesDTO2(DesignationModules designationModules) {
		DesignationModulesDTO designationModulesDTO = new DesignationModulesDTO(
				designationModules.getId(),
				designationModules.getCreatedBy(),
				designationModules.getIpaddress(),
				designationModules.isStatus(),
				designationModules.getDesignations() != null ? designationModules.getDesignations().getId() : null,
				designationModules.getDesignations() != null ? designationModules.getDesignations().getDesignationName() : null,
				designationModules.getModules() != null ? designationModules.getModules().getId() : null,
				designationModules.getModules() != null ? designationModules.getModules().getModuleName() : null
						);
		return designationModulesDTO;
		
	}
	
	// Get DesignationModules by Modules Id
	public Response<Object> getDesignationModulesByModulesId(Long modulesId){
		List<DesignationModules> designationModulesList = designationModulesRepository.findByModulesId(modulesId);
		if(designationModulesList.isEmpty()) {
			throw new CustomException("Not Found", "404");
		}
		
		List<DesignationModulesDTO> designationModulesDTOList = designationModulesList.stream()
				.map(this::convertToDesignationModulesDTO).collect(Collectors.toList());
		return new Response<>("Successful", "Getting DesignationModules by modulesId ", designationModulesDTOList);
		
		
	}
	
	private DesignationModulesDTO convertToDesignationModulesDTO3(DesignationModules designationModules) {
		
		DesignationModulesDTO designationModulesDTO = new DesignationModulesDTO(
				designationModules.getId(),
				designationModules.getCreatedBy(),
				designationModules.getIpaddress(),
				designationModules.isStatus(),
				designationModules.getDesignations() != null ? designationModules.getDesignations().getId() : null,
				designationModules.getDesignations() != null ? designationModules.getDesignations().getDesignationName() : null,
				designationModules.getModules() != null ? designationModules.getModules().getId() : null,
				designationModules.getModules() != null ? designationModules.getModules().getModuleName() : null
						);
		return designationModulesDTO;
	}
	
	
	
	public DesignationModules addDesignationModules(DesignationModules designationModules) {
	    if (designationModules.getDesignations() == null || designationModules.getDesignations().getId() == null) {
	        throw new CustomException("Designations ID cannot be null.", "DESIGNATIONS_ID_REQUIRED");
	    }

	    if (designationModules.getModules() == null || designationModules.getModules().getId() == null) {
	        throw new CustomException("Modules ID cannot be null.", "MODULES_ID_REQUIRED");
	    }

	    Designations designation = designationsRepository.findById(designationModules.getDesignations().getId())
	            .orElseThrow(() -> new CustomException("Designations with ID " + designationModules.getDesignations().getId() + " does not exist.", "DESIGNATIONS_NOT_FOUND"));
	    designationModules.setDesignations(designation);
	    
	    
	    Modules module = modulesRepository.findById(designationModules.getModules().getId())
	            .orElseThrow(() -> new CustomException("Modules with ID " + designationModules.getModules().getId() + " does not exist.", "MODULES_NOT_FOUND"));
	    designationModules.setModules(module);
	    
	    return designationsRepository.save(designationModules);
	}


	
	
	
	public DesignationModules findById(Long id) {
	    return designationModulesRepository.findById(id)
	            .orElseThrow(() -> new CustomException("DesignationModule not found", "DESIGNATIONMODULE_NOT_FOUND"));
	}

	public DesignationModules updateDesignationModule(Long id, DesignationModules updatedDesignationModule) {
	    DesignationModules existing = findById(id); 
	    existing.setCreatedBy(updatedDesignationModule.getCreatedBy());
	    existing.setModifiedBy(updatedDesignationModule.getModifiedBy());
	    existing.setIpaddress(updatedDesignationModule.getIpaddress());
	    existing.setStatus(updatedDesignationModule.isStatus());
	    existing.setIsdelete(updatedDesignationModule.isIsdelete());
	    existing.setDesignations(updatedDesignationModule.getDesignations());
	    existing.setModules(updatedDesignationModule.getModules());
	    return designationModulesRepository.save(existing);
	}
}