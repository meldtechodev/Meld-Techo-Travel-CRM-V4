package com.meld.techo.travel.crm.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.meld.techo.travel.crm.models.Modules;
import com.meld.techo.travel.crm.security.service.ModulesService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("Motherson/crm/v1/modules")
public class ModulesController {

	@Autowired
	private ModulesService modulesService;


	@GetMapping("/getall")
	public ResponseEntity<List<Modules>> getAllModules(){
		List<Modules> module = modulesService.getAllModules();
		return new ResponseEntity(module, HttpStatus.OK);
	}

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<?> getModulesById(@PathVariable Long id){
		Optional<Modules> existingmodule = modulesService.getModulesById(id);
		if(existingmodule.isPresent()) {
			return new ResponseEntity<>(existingmodule.get(), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(" ID " + id + "Not present in the Database", HttpStatus.NOT_FOUND);
		}
	}


	@PostMapping("/create")
	public ResponseEntity<?> addModules(@RequestBody Modules modules){
		try
		{
			Modules modu = modulesService.addModules(modules);
			return new ResponseEntity<>(modu, HttpStatus.CREATED);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}


	@PutMapping("/update/{id}")
    public ResponseEntity<Object> updateModule(@PathVariable Long id, @Valid @RequestBody Modules updatedModule) {
        // Call the service to update the module
        Modules updated = modulesService.updateModule(id, updatedModule);
        // If the module was not found, return a NOT_FOUND response
        if (updated == null) {
            return new ResponseEntity<>("Module with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        // Return the updated module in the response with OK status
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }



	@PutMapping("/deleteby/{id}")
	public ResponseEntity<?> deleteModules(@PathVariable Long id){
		modulesService.deleteModules(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	}


