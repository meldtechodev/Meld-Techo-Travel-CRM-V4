package com.meld.techo.travel.crm.security.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.models.Modules;
import com.meld.techo.travel.crm.repository.ModulesRepository;

import jakarta.persistence.EntityNotFoundException;
@Service
public class ModulesService {

	@Autowired
	private ModulesRepository modulesRepository;


	public Modules addModules(Modules modules) {
		if(modulesRepository.existsByModuleName(modules.getModuleName())) {
			throw new IllegalArgumentException("Modules With This Name" + modules.getModuleName() + "already exists.");
		}
		return modulesRepository.save(modules);
	}


	public List<Modules> getAllModules(){
		List<Modules> modul = modulesRepository.findAll();
		return modul.stream()
				.filter(modules -> !modules.isIsdelete())
				.collect(Collectors.toList());
	}


	public Optional<Modules> getModulesById(Long id){
		return modulesRepository.findById(id);
	}
	public Modules getModuleById(Long id) {
        Optional<Modules> module = modulesRepository.findById(id);
        return module.orElse(null);  
    }
 
	public Modules updateModule(Long id, Modules updatedModule) {
        Optional<Modules> existingModule = modulesRepository.findById(id);
        if (!existingModule.isPresent()) {
            return null;
        }
        Modules module = existingModule.get();
        // Update the fields with the new values
        module.setModuleName(updatedModule.getModuleName());
        module.setParentId(updatedModule.getParentId());
        module.setCreatedBy(updatedModule.getCreatedBy());
        module.setModifiedBy(updatedModule.getModifiedBy());
        module.setIpaddress(updatedModule.getIpaddress());
        module.setStatus(updatedModule.isStatus());
        module.setIsdelete(updatedModule.isIsdelete());
       // module.setPermissions(updatedModule.getPermissions()); // Update permissions if needed
        // Save the updated module
        return modulesRepository.save(module);
    }


	public String deleteModules(Long id) {
		Modules existingModule = modulesRepository.findById(id)
				.orElseThrow(()  -> new RuntimeException(" Modules Not Founds" ));

		existingModule.setIsdelete(true);
//		existingModule.setPermissions(existingModule.getPermissions());
		modulesRepository.save(existingModule);
		return " This Module is deleted with this ";
	}}

//	
//}
