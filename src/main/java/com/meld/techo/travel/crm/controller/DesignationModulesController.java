package com.meld.techo.travel.crm.controller;

import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.ErrorResponse;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.DesignationModules;
import com.meld.techo.travel.crm.models.Designations;
import com.meld.techo.travel.crm.models.Modules;
import com.meld.techo.travel.crm.security.service.DesignationModulesService;
import com.meld.techo.travel.crm.security.service.DesignationsService;
import com.meld.techo.travel.crm.security.service.ModulesService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
 
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/designationModules")
public class DesignationModulesController {
	
	
	@Autowired
	private DesignationModulesService designationModulesService;
	
	@Autowired
	private DesignationsService designationsService;
	
	@Autowired
	private ModulesService modulesService;
	
	
	@GetMapping("/all")
	public ResponseEntity<Response<Object>> getAllDesignationModules(){
		try {
			Response<Object> response = designationModulesService.getAllDesignationModules();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<Object>> getDesignationModulesId(@PathVariable Long id){
		try {
			Response<Object> response = designationModulesService.getDesignationModulesById(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	@GetMapping("/designations/{designationsId}")
	public ResponseEntity<Response<Object>> getDesignationModulesByDesignationsId(@PathVariable Long designationsId){
		try {
			Response<Object> response = designationModulesService.getDesignationModulesByDesignationsId(designationsId);
					return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	@GetMapping("/modules/{modulesId}")
	public ResponseEntity<Response<Object>> getDesignationModulesByModulesId(@PathVariable Long modulesId){
		try {
			Response<Object> response = designationModulesService.getDesignationModulesByModulesId(modulesId);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	@PostMapping("")
	public ResponseEntity<Response<?>> addDesignationModules(@RequestBody @Valid DesignationModules designationModules, HttpServletRequest request){
		
		String clientIpAddress = getClientIp(request);
		designationModules.setIpaddress(clientIpAddress);
		
		try {
			
	DesignationModules designationModule = designationModulesService.addDesignationModules(designationModules);
	
	Response<DesignationModules> response = new Response<>("Successful", "Create Successfully", designationModule);
	
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}catch (CustomException e) {
			
			ErrorResponse errorResponse = new ErrorResponse("FAILURE", e.getMessage(), e.getErrorCode());
			Response<ErrorResponse> response = new Response<>("FAILURE","Validation error", errorResponse);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			
		}catch(Exception e) {
			ErrorResponse errorResponse = new ErrorResponse("FAILURE", "An unexpected error occurred: " + e.getMessage(), "500");
		    Response<ErrorResponse> response = new Response<>("FAILURE","Internal server error",  errorResponse);
		    
		    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
	}



	@PutMapping("/{id}")
	public ResponseEntity<Response<DesignationModules>> updateDesignationModule(
	        @PathVariable("id") Long id,
	        @RequestBody DesignationModules updatedDesignationModule, HttpServletRequest request) {
		
		
		String clientIpAddress = getClientIp(request);
		updatedDesignationModule.setIpaddress(clientIpAddress);

	    
	    Designations designations = designationsService.getDesignationById(updatedDesignationModule.getDesignations().getId());
	    if (designations == null) {
	        throw new CustomException("Designation not found", "DESIGNATION_NOT_FOUND");
	    }

	    Modules modules = modulesService.getModuleById(updatedDesignationModule.getModules().getId());
	    if (modules == null) {
	        throw new CustomException("Module not found", "MODULE_NOT_FOUND");
	    }

	   
	    DesignationModules existingDesignationModule = designationModulesService.findById(id);
	    if (existingDesignationModule == null) {
	        throw new CustomException("DesignationModule not found", "DESIGNATIONMODULE_NOT_FOUND");
	    }

	    
	    existingDesignationModule.setCreatedBy(updatedDesignationModule.getCreatedBy());
	    existingDesignationModule.setModifiedBy(updatedDesignationModule.getModifiedBy());
	    existingDesignationModule.setIpaddress(updatedDesignationModule.getIpaddress());
	    existingDesignationModule.setStatus(updatedDesignationModule.isStatus());
	    existingDesignationModule.setIsdelete(updatedDesignationModule.isIsdelete());
	    existingDesignationModule.setDesignations(designations);
	    existingDesignationModule.setModules(modules);

	    
	    DesignationModules updated = designationModulesService.updateDesignationModule(id, existingDesignationModule);

	    
	    Response<DesignationModules> response = new Response<>("success", "DesignationModule updated successfully", updated);
	    return ResponseEntity.ok(response);
	}



private String getClientIp(HttpServletRequest request) {
    String ipAddress = request.getHeader("X-Forwarded-For");
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
        ipAddress = request.getRemoteAddr();
    }
    return ipAddress;
}
}
