package com.meld.techo.travel.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.State;



@Repository
public interface StateRepository extends JpaRepository<State,Long>

{

	
	
	
	List<State> findByIsdeleteFalse();
	
     boolean existsByStateName(String stateName);
	
     Optional<State> findById(Long id);
     List<State> findByCountryId(Long countryId);

	List<State> findByIdAndCountryId(Long Id, Long countryId);
	

    List<State> findByCountryIdAndIsdelete(Long countryId, boolean isDelete);
    List<State> findByIsdelete(boolean isDelete);


	Page<State> findByIsdeleteFalse(PageRequest pageable);
	
}
