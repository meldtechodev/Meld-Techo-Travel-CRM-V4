package com.meld.techo.travel.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.Activities;


@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, Long> {
	
	boolean existsByTitle(String title);

}
