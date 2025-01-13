package com.meld.techo.travel.crm.security.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CityDTO;
import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.City;
import com.meld.techo.travel.crm.repository.CityRepository;




@Service
public class CityService<Country> {
	
	@Autowired
	private CityRepository cityRepository;
	
	
	
	
	public City createdcity(City city){
		return cityRepository.save(city);
	}
	
	public boolean existsByCityName(String cityName)
	{
		return cityRepository.existsByCityName(cityName);
	}
	
	
	
	
	public Page<CityDTO> getCity(int page, int size, String sortDirection) {
	    
	    Sort sort = Sort.by(Sort.Order.asc("cityName"));
	    
	    if ("desc".equalsIgnoreCase(sortDirection)) {
	        sort = Sort.by(Sort.Order.desc("cityName"));
	    }

	    
	    PageRequest pageable = PageRequest.of(page, size, sort);
	  
	    Page<City> citys = cityRepository.findAll(pageable);

	    List<CityDTO> filteredCity = citys.stream()
	            .filter(city -> !city.isIsdelete())
	            .map(this::convertToCityDTO)
	            .collect(Collectors.toList());

	    return new PageImpl<>(filteredCity, pageable, citys.getTotalElements());
	}

	private CityDTO convertToCityDTO(City city) {
	    return new CityDTO(
	    		city.getId(),
	    		city.getCityName(),
	    		city.isStatus(),
	    		city.getCreated_date(),
	    		city.getModified_date(),
	    		city.getKeyofattractions(),
	    		city.getD_image(),
	    		city.getCountry() != null ? city.getCountry().getId() : null,
	    		city.getCountry() != null ? city.getCountry().getCountryName() : null,
	    		city.getState() != null ? city.getState().getId() : null,
	    		city.getState() != null ? city.getState().getStateName() : null
	    );
	}

	
	
	
	
	
	
	public Optional <City> getCitysById(long id)	{
		return cityRepository.findById(id);
	}
	

	
	public City updateCityById(Long id, City cityDetails) {
        City existingCity = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
 
        existingCity.setCityName(cityDetails.getCityName());
        
        existingCity.setD_image(cityDetails.getD_image());
        //existingDestination.setIpaddress(destinationDetails.getIpaddress());
        existingCity.setStatus(cityDetails.isStatus());
        existingCity.setCreated_date(cityDetails.getCreated_date());
        existingCity.setModified_date(cityDetails.getModified_date());
        existingCity.setCreated_by(cityDetails.getCreated_by());
        existingCity.setModified_by(cityDetails.getModified_by());
        existingCity.setIsdelete(cityDetails.isIsdelete());
        existingCity.setKeyofattractions(cityDetails.getKeyofattractions());
        return cityRepository.save(existingCity);
    }
 
	
	public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
    }

	
	

	
	
	
	public String deleteCity(Long id) {
	 
		City existingCity = cityRepository.findById(id)
	            .orElseThrow(() -> new CustomException("City not found", "404"));
	    existingCity.setIsdelete(true); 

	    
	    cityRepository.save(existingCity);

	    return "data deleted"; 
	}

	
	
	

 public Response<Object> getAllCity(){
	 List<City> citys = cityRepository.findAll().stream()
			       .filter(city -> !city.isIsdelete())
			       .collect(Collectors.toList());
	   if(citys.isEmpty()) {
		   throw new CustomException("Not Getting City", "404");
       }
	   
		   
		   List<CityDTO> cityDTOList = citys.stream()
				   .map(this::convertToCityDTO1).collect(Collectors.toList());
		   
		   return new Response<>("Successful", "Getting All City Successfully", cityDTOList);
		   
	   }
 
 private CityDTO convertToCityDTO1(City city) {
	 
	 return new CityDTO(
			 city.getId(),
	    		city.getCityName(),
	    		city.isStatus(),
	    		city.getCreated_date(),
	    		city.getModified_date(),
	    		city.getKeyofattractions(),
	    		city.getD_image(),
	    		city.getCountry() != null ? city.getCountry().getId() : null,
	    		city.getCountry() != null ? city.getCountry().getCountryName() : null,
	    		city.getState() != null ? city.getState().getId() : null,
	    		city.getState() != null ? city.getState().getStateName() : null
	    );
	}
 
 
 public Response<Object> getCityByCountryId(Long countryId){
	 List<City> citys = cityRepository.findByCountryId(countryId);
	 if(citys.isEmpty()) {
		// return new Response<>("Failure", "Not Found Destination with this ContryId" +countryId, null);
		 throw new CustomException("Not Getting City", "404");
     }
	 
	 
	 List<CityDTO> cityDTOList = citys.stream().map(this::convertToCityDTO2).collect(Collectors.toList());
	 return new Response<>("Successful", "Getting City Successfully", cityDTOList);
	 }
 
 private CityDTO convertToCityDTO2(City city) {
	 return new CityDTO(
			 city.getId(),
	    		city.getCityName(),
	    		city.isStatus(),
	    		city.getCreated_date(),
	    		city.getModified_date(),
	    		city.getKeyofattractions(),
	    		city.getD_image(),
	    		city.getCountry() != null ? city.getCountry().getId() : null,
	    		city.getCountry() != null ? city.getCountry().getCountryName() : null,
	    		city.getState() != null ? city.getState().getId() : null,
	    		city.getState() != null ? city.getState().getStateName() : null
	    );
	}
 
 
 public Response<Object> getCityByStateId(Long stateId){
	 List<City> citys = cityRepository.findByStateId(stateId);
	 if(citys.isEmpty()) {
		 throw new CustomException("City Not Found", "404");
	 }
	 
	 List<CityDTO> cityDTOList = citys.stream().map(this::convertToCityDTO3).collect(Collectors.toList());
	 return new Response<>("Successful", "Getting City successfully by stateId", cityDTOList );
 }
 
 private CityDTO convertToCityDTO3(City city) {
	 //return new DestinationDTO(
	 CityDTO cityDTO = new CityDTO(
			 city.getId(),
	    		city.getCityName(),
	    		city.isStatus(),
	    		city.getCreated_date(),
	    		city.getModified_date(),
	    		city.getKeyofattractions(),
	    		city.getD_image(),
	    		city.getCountry() != null ? city.getCountry().getId() : null,
	    		city.getCountry() != null ? city.getCountry().getCountryName() : null,
	    		city.getState() != null ? city.getState().getId() : null,
	    		city.getState() != null ? city.getState().getStateName() : null
	    );
	
	 return cityDTO;
		    		 
		    		  
 }
	
}
