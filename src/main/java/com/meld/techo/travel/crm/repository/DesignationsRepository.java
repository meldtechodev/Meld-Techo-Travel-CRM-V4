package com.meld.techo.travel.crm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.DesignationModules;
import com.meld.techo.travel.crm.models.Designations;

@Repository
public interface DesignationsRepository extends JpaRepository<Designations, Long> {
	boolean existsByDesignationName(String designationName);
    Page<Designations> findAll(Pageable pageable);
	List<Designations> findByDepartmentsId(Long departmentsId);
	DesignationModules save(DesignationModules designationModules);
}

