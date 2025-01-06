package com.meld.techo.travel.crm.security.service;

import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.models.DesignationModules;
import com.meld.techo.travel.crm.models.Designations;
import com.meld.techo.travel.crm.repository.DesignationModulesRepository;
import com.meld.techo.travel.crm.repository.DesignationsRepository;

import jakarta.persistence.EntityNotFoundException;
 
@Service
public class DesignationModulesService {

	@Autowired
	private DesignationModulesRepository designationModulesRepository;

	@Autowired
	private DesignationsRepository designationsRepository;

	public List<DesignationModules>  getAllDesignationModules()
	{
		List<DesignationModules> allPerm = designationModulesRepository.findAll();
		return allPerm;
	}
 
 
	
	public Optional<DesignationModules> getDesignationModulesById(Long id)
	{
		Optional<DesignationModules> dModule = designationModulesRepository.findById(id);
		return dModule;
	}

	public DesignationModules addDesignationModules(DesignationModules designationModules)
	{
		DesignationModules deModules = designationModulesRepository.save(designationModules);
		return deModules;
	}
 
	

 
	
	public DesignationModules updateDesignationModules(Long id, DesignationModules updatedDesignationModules) {
        // Find the existing DesignationModules by id
        DesignationModules existingDesignationModules = designationModulesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DesignationModule not found with id: " + id));
 
        // Update the fields of the existing entity with the new values from the request body
        existingDesignationModules.setCreatedBy(updatedDesignationModules.getCreatedBy());
        existingDesignationModules.setModifiedBy(updatedDesignationModules.getModifiedBy());
        existingDesignationModules.setIpaddress(updatedDesignationModules.getIpaddress());
        existingDesignationModules.setStatus(updatedDesignationModules.isStatus());
        existingDesignationModules.setIsdelete(updatedDesignationModules.isIsdelete());
        existingDesignationModules.setModules(updatedDesignationModules.getModules());
 
        // Convert DesignationsDTO to Designations entity and set it
        Designations designations = designationsRepository.findById(updatedDesignationModules.getDesignations().getId())
                .orElseThrow(() -> new RuntimeException("Designation not found"));
        existingDesignationModules.setDesignations(designations);
 
        // Save and return the updated entity
        return designationModulesRepository.save(existingDesignationModules);
    }
}

