package com.meld.techo.travel.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.DesignationModules;
 

 
@Repository
public interface DesignationModulesRepository extends JpaRepository<DesignationModules, Long> {

	List<DesignationModules> findByDesignationsId(Long designationsId);

	List<DesignationModules> findByModulesId(Long modulesId);

}