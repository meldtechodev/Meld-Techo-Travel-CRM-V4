package com.meld.techo.travel.crm.repository;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.City;



@Repository
public interface CityRepository extends JpaRepository<City,Long > {

	List<City> findByIsdeleteFalse();
	boolean existsByCityName(String cityName);


	


	Optional<City> findById(Long cityId);
    List<City> findByStateId(Long stateId);
    List<City> findByCountryId(Long countryId);
    List<City> findByStateIdAndCountryId(Long stateId, Long countryId);
	//Optional<Destination> findBydestinationId(Long destinationId);


	
	   
}