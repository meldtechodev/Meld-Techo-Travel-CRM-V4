package com.meld.techo.travel.crm.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meld.techo.travel.crm.models.Departments;
import com.meld.techo.travel.crm.security.service.DepartmentsService;

import jakarta.persistence.EntityNotFoundException;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/departments")
public class DepartmentsController {

	@Autowired
	private DepartmentsService departmentsService;


	@GetMapping("/getalldepartments")
	public ResponseEntity<List<Departments>> getAllDepartments() {
        List<Departments> depart = departmentsService.getAllDepartments();
        return new ResponseEntity<>(depart, HttpStatus.OK);
    }

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<Departments> getDepartmentsById(@PathVariable Long id){
		Optional<Departments> depart = departmentsService.getDepartmentsById(id);
		return depart.map(value  -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}



	@GetMapping("/getall")
	public Page<Departments> getDepartments(
		    @RequestParam(value = "page", defaultValue = "0") int page,  // Default page is 0
		    @RequestParam(value = "size", defaultValue = "10") int size, // Default size is 10
		    @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection // Default sort direction is ascending
		) {
		    return departmentsService.getDepartments(page, size, sortDirection);  // Call the service method with pagination and sorting parameters
		}



	@PostMapping("/create")
    public ResponseEntity<?> addDepartments(@RequestBody Departments departments) {
        try {
            Departments depart = departmentsService.addDepartments(departments);
            return new ResponseEntity<>(depart, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage()); // Return the error message if department name already exists
        }
    }


	@PutMapping("/updatebyid/{id}")
	public ResponseEntity<Departments> updateDepartments(@PathVariable Long id, @RequestBody Departments departments){
		if(departments != null)
		{
			Departments depart = new Departments();
			depart.setId(id);
			depart.setDepartmentName(departments.getDepartmentName());
			depart.setCreatedBy(departments.getCreatedBy());
			depart.setModifiedBy(departments.getModifiedBy());
			depart.setIpaddress(departments.getIpaddress());
			depart.setStatus(departments.isStatus());
			depart.setIsdelete(departments.isIsdelete());

			departmentsService.updateDepartments(depart);
			return ResponseEntity.ok(depart);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}




	@PutMapping("/deleteby/{id}")
	public ResponseEntity<?> deleteDepartments(@PathVariable Long id) {
		departmentsService.deleteDepartments(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	}

