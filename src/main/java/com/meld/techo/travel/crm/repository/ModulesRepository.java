package com.meld.techo.travel.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.Modules;
 

 
@Repository
public interface ModulesRepository extends JpaRepository<Modules, Long> {
 
	boolean existsByModuleName(String moduleName);
 
	//boolean existsByModuleName(String moduleName);
 
}