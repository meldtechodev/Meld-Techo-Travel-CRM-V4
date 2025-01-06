package com.meld.techo.travel.crm.controller;

import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meld.techo.travel.crm.dto.DesignationsDTO;
import com.meld.techo.travel.crm.models.Departments;
import com.meld.techo.travel.crm.models.Designations;
import com.meld.techo.travel.crm.security.service.DepartmentsService;
import com.meld.techo.travel.crm.security.service.DesignationsService;
 

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/designations")
public class DesignationsController {

	@Autowired
	private DesignationsService designationsService;

	@Autowired
	private DepartmentsService departmentsService;


 
	
	@GetMapping("/getallandbyid")
	public ResponseEntity<Object> getAllDesignations(
	        @RequestParam(required = false) Long id,
	        @RequestParam(required = false) Long departmentsId) {
	    // If an id is provided, fetch the designation by id
	    if (id != null) {
	        DesignationsDTO designationDTO = designationsService.getDesignationById(id);
	        if (designationDTO != null) {
	            return ResponseEntity.ok(designationDTO);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    // If a departmentId is provided, fetch designations by departmentId
	    if (departmentsId != null) {
	        List<DesignationsDTO> designationsDTOs = designationsService.getDesignationsByDepartmentsId(departmentsId);
	        if (!designationsDTOs.isEmpty()) {
	            return ResponseEntity.ok(designationsDTOs);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    // If neither id nor departmentId is provided, fetch all designations
	    List<DesignationsDTO> designationsDTOs = designationsService.getAllDesignations();
	    return ResponseEntity.ok(designationsDTOs);
	}



	@GetMapping("/getall")
	public Page<Designations> getDesignations(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "sortDirection" , defaultValue =  "asc") String sortDirection
			){
		return designationsService.getDesignations(page, size, sortDirection);
	}

	@PostMapping("/create")
    public ResponseEntity<?> addDesignations(@RequestBody Designations designations) {
        try {
        	Designations desig = designationsService.addDesignations(designations);
            return new ResponseEntity<>(desig, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage()); // Return the error message if department name already exists
        }}


	@PutMapping("/update/{id}")
	public ResponseEntity<Designations> updateDesignation(
	        @PathVariable("id") Long id,
	        @RequestBody Designations updatedDesignation) {
	    // Ensure the department exists in the database
	    Departments department = departmentsService.getDepartmentById(updatedDesignation.getDepartments().getId());
	    if (department == null) {
	        return ResponseEntity.badRequest().body(null); // or throw an exception
	    }
	    updatedDesignation.setDepartments(department);
	    Designations updated = designationsService.updateDesignation(id, updatedDesignation);
	    if (updated != null) {
	        return ResponseEntity.ok(updated);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}



	 @PutMapping("/deleteby/{id}")
		public ResponseEntity<?> deleteDesignations(@PathVariable Long id) {
		 designationsService.deleteDesignations(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
