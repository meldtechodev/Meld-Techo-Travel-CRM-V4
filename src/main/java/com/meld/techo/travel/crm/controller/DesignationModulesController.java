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

import com.meld.techo.travel.crm.models.DesignationModules;
import com.meld.techo.travel.crm.security.service.DesignationModulesService;
import com.meld.techo.travel.crm.security.service.DesignationsService;
import com.meld.techo.travel.crm.security.service.ModulesService;

import jakarta.persistence.EntityNotFoundException;
 
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

	@GetMapping("/getall")
	public List<DesignationModules> getAllDesignationModules()
	{
		List<DesignationModules> dPer = designationModulesService.getAllDesignationModules();
		return dPer;
	}

	@GetMapping("/getbyid/{id}")
	public Optional<DesignationModules> getDesignationModulesById(@PathVariable Long id)
	{
		Optional<DesignationModules> getModu = designationModulesService.getDesignationModulesById(id);
		return getModu;
	}

	@PostMapping("/create")
	public ResponseEntity<String> addDesignationModules(@RequestBody DesignationModules designationModules)
	{
		DesignationModules dModules = designationModulesService.addDesignationModules(designationModules);
		return ResponseEntity.status(HttpStatus.CREATED).body(" DesignationModules Successfully created");
	}



	@PutMapping("/update/{id}")
    public ResponseEntity<String> updateDesignationModules(@PathVariable Long id, @RequestBody DesignationModules designationModules) {
        try {
            designationModulesService.updateDesignationModules(id, designationModules);
            return ResponseEntity.status(HttpStatus.OK).body("DesignationModule successfully updated");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DesignationModule not found with id: " + id);
        }
    }

 
}