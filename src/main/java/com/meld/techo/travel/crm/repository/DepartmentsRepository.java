package com.meld.techo.travel.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.Departments;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Long> {
	boolean existsByDepartmentName(String departmentName);
	Page<Departments> findAll(Pageable pageable);

}

