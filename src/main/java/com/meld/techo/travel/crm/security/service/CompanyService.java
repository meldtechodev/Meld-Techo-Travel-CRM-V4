package com.meld.techo.travel.crm.security.service;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.dto.Response;
import com.meld.techo.travel.crm.models.Company;
import com.meld.techo.travel.crm.models.Departments;
import com.meld.techo.travel.crm.repository.CompanyRepository;

import io.jsonwebtoken.io.IOException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Path;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CompanyService {
	@Autowired
	 private CompanyRepository companyrepository;
	
	
	
	@Autowired
	private HttpServletRequest request;
	
	
	
	
	// Get all with pageable
		public Page<Company> getCompany(int page, int size, String sortDirection) {
		    Sort sort = Sort.by(Sort.Order.asc("companyname"));
		    if ("desc".equalsIgnoreCase(sortDirection)) {
		        sort = Sort.by(Sort.Order.desc("companyname"));
		    }

		    PageRequest pageable = PageRequest.of(page, size, sort);
		    
		    Page<Company> companyPage = companyrepository.findAll(pageable);
		    List<Company> filteredCompany = companyPage.getContent().stream()
		            .filter(company -> !company.isIsdelete())  
		            .collect(Collectors.toList());

		    return new PageImpl<>(filteredCompany, pageable, companyPage.getTotalElements());
		}
	
		
		// Get All
	public Response<Object>	getAllCompany(){
		List<Company> companyList = companyrepository.findAll().stream()
				.filter(company -> !company.isIsdelete())
				.collect(Collectors.toList());
		if(companyList.isEmpty()) {
			throw new CustomException("Not Found", "404");
		}
		return new Response<>("Success", "Getting All Departments Successfully", companyList);
	}
	
	
	public Response<Object> getCompanysById(Long id){
	  Optional<Company> companyOptional = companyrepository.findById(id);
	  
	  if(companyOptional.isEmpty()) {
		  
		  throw new CustomException("Company Not Found With this id", "404");
		  
	  }
	  return new Response<>("Success", "Getting Company Successsfully", companyOptional);
	}
	
	
		
	// Create 
	public Company createcompanyser(Company company)

	{
		String ipAddress = getClientIp(request);
	     Company.setUserIpAddress(ipAddress);
	     
		   Company companysave= companyrepository.save(company);
		return companysave ;
	}
	
	
	//Update
	public Company updateCompanyById(Long id, Company companyDetails) {
        Company existingCompany = companyrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
 
        existingCompany.setCompanyname(companyDetails.getCompanyname());
        existingCompany.setCompanyaddress(companyDetails.getCompanyaddress());
        existingCompany.setCompanycountrycode(companyDetails.getCompanycountrycode());
        existingCompany.setCompanyphone(companyDetails.getCompanyphone());
        existingCompany.setCompanyemail(companyDetails.getCompanyemail());
        existingCompany.setCompanywebsite(companyDetails.getCompanywebsite());
        existingCompany.setCreatedby(companyDetails.getCreatedby());
        existingCompany.setCompanylogo(companyDetails.getCompanylogo());
        existingCompany.setStatus(companyDetails.isStatus());
        existingCompany.setModifiedby(companyDetails.getModifiedby());
        existingCompany.setModifieddate(companyDetails.getModifieddate());
        existingCompany.setIpaddress(companyDetails.getIpaddress());
        existingCompany.setIsdelete(companyDetails.isIsdelete());
        existingCompany.setParent_id(companyDetails.getParent_id());
        return companyrepository.save(existingCompany);

	}
	public Company getCompanyById(Long id) {
        return companyrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

	
	public String deleteCompany(Long id) {
		Company existingCompany = companyrepository.findById(id)
		            .orElseThrow(() -> new CustomException("Company not found", "404"));
		existingCompany.setIsdelete(true); 

		    
		companyrepository.save(existingCompany);

		    return "data deleted"; 
		}




	




	public Company getcompanysbyid(Long id) {
		Optional<Company>compid=companyrepository.findById(id);
		if(compid.isPresent())
		{
		 Company complogo=	compid.get();
		 return complogo;
		}
		else
		{
	        // Handle case where company is not found
	        throw new EntityNotFoundException("Company with ID " + id + " not found.");
	    }
	}

	

	private String getClientIp(HttpServletRequest request) {
  String ipAddress = request.getHeader("X-Forwarded-For");
  if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getRemoteAddr();
  }
  return ipAddress;
}
}
	
	
	


