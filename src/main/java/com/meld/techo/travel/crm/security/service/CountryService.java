package com.meld.techo.travel.crm.security.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CountryDTO;
import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.models.Company;
import com.meld.techo.travel.crm.models.Country;
import com.meld.techo.travel.crm.models.Departments;
import com.meld.techo.travel.crm.repository.CountryRepository;

import jakarta.servlet.http.HttpServletRequest;

//import com.MotherSon.CRM.models.Country;
//import com.MotherSon.CRM.repository.CountryRepository;

@Service

public class CountryService {

	@Autowired
    private CountryRepository countryRepository;
	
	
	
//	public List<Country> getAllCountries() {
//        // Fetch all countries
//        List<Country> countries = countryRepository.findAll();
//        
//        // Filter countries where isDelete is false (not deleted)
//        return countries.stream()
//                        .filter(country -> !country.isIsdelete())  // Keep countries where isDelete is false
//                        .collect(Collectors.toList());
//    }
	public List<CountryDTO> getAllCountries() {
	    List<Country> countries = countryRepository.findAll();

	    // If no countries found, throw CustomException
	    if (countries.isEmpty()) {
	        throw new CustomException("No countries found", "404");
	    }

	    return countries.stream()
	                    .filter(country -> !country.isIsdelete())
	                    .map(country -> new CountryDTO(
	                        country.getId(),
	                        country.getCountryName(),
	                        country.getCode(),
	                        country.getPcode(),
	                        country.isStatus(),
	                        country.getCimage(),
	                        country.getCreateddate()
	                    ))
	                    .collect(Collectors.toList());
	}



	
	
	public Page<Country> getCountry(int page, int size, String sortDirection) {
	    // Validate input parameters
	    if (page < 0) {
	        throw new CustomException("Page number cannot be negative", "400");
	    }
	    if (size <= 0) {
	        throw new CustomException("Page size must be greater than zero", "400");
	    }
	    if (!"asc".equalsIgnoreCase(sortDirection) && !"desc".equalsIgnoreCase(sortDirection)) {
	        throw new CustomException("Sort direction must be either 'asc' or 'desc'", "400");
	    }

	    Sort sort = Sort.by(Sort.Order.asc("countryName"));
	    if ("desc".equalsIgnoreCase(sortDirection)) {
	        sort = Sort.by(Sort.Order.desc("countryName"));
	    }

	    PageRequest pageable = PageRequest.of(page, size, sort);

	    // Fetch paginated results from repository
	    Page<Country> countries = countryRepository.findAll(pageable);

	    // Check if the result is empty
	    if (countries.isEmpty()) {
	        throw new CustomException("No countries found for the given page and size", "404");
	    }

	    return countries;
	}



	public CountryDTO getCountrysById(Long id) {
	    // Check if the country exists
	    Country country = countryRepository.findById(id)
	        .orElseThrow(() -> new CustomException("Country not found with ID: " + id, "404"));

	    // Map Country to CountryDTO (use a utility method or manual mapping)
	    return mapCountryToDTO(country);
	}

	// Utility method to map Country to CountryDTO
	private CountryDTO mapCountryToDTO(Country country) {
	    return new CountryDTO(
	    		 country.getId(),
                 country.getCountryName(),
                 country.getCode(),
                 country.getPcode(),
                 country.isStatus(),
                 country.getCimage(),
                 country.getCreateddate()
	    );
	}

    public boolean existsByCountryName(String countryName) {
        return countryRepository.existsByCountryName(countryName);
    }

    public Country addCountry(Country country) {
    	
    	 
        return countryRepository.save(country);
    }
    
    
    



    
    public Country updateCountryById(Long id, Country countryDetails) {
        Country existingCountry = countryRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Country not found"));
        		

        existingCountry.setCountryName(countryDetails.getCountryName());
        existingCountry.setCode(countryDetails.getCode());
        existingCountry.setPcode(countryDetails.getPcode());
        existingCountry.setIpaddress(countryDetails.getIpaddress());
        existingCountry.setCimage(countryDetails.getCimage());
        existingCountry.setStatus(countryDetails.isStatus());
        existingCountry.setCreatedby(countryDetails.getCreatedby());
        existingCountry.setModifiedby(countryDetails.getModifiedby());
        existingCountry.setModifieddate(countryDetails.getModifieddate());
        existingCountry.setIsdelete(countryDetails.isIsdelete());
        return countryRepository.save(existingCountry);
    }

    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
    }
    
    
    

	public String deleteCountry(Long id) {
		Country existingCountry = countryRepository.findById(id)
		            .orElseThrow(() -> new CustomException("Country not found", "404"));
		existingCountry.setIsdelete(true); 

		    
		countryRepository.save(existingCountry);

		    return "data deleted"; 
		}



}

		