package com.meld.techo.travel.crm.security.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.models.Company;
import com.meld.techo.travel.crm.repository.CompanyRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CompanyService {
	@Autowired
	 private CompanyRepository companyrepository;
	public Company createcompanyser(Company company)
	{
		   Company companysave= companyrepository.save(company);
		return companysave ;
	}
	public List<Company> getAllCompany() {
       // Fetch all countries
       List<Company> companies = companyrepository.findAll();
       // Filter countries where isDelete is false (not deleted)
       return companies.stream()
                       .filter(company -> !company.isIsdelete())  // Keep countries where isDelete is false
                       .collect(Collectors.toList());
   }

	

	public Page<Company> getCompany(int page, int size, String sortDirection){
		Sort sort = Sort.by(Sort.Order.asc("companyname"));
		if("desc".equalsIgnoreCase(sortDirection)) {
			sort = Sort.by(Sort.Order.desc("companyname"));
		}
		PageRequest pageable = PageRequest.of(page, size, sort);
		return companyrepository.findAll(pageable);
		}



	public Optional<Company> getcompanybyid(Long id)
	{
		 Optional<Company> getcompanybyid=companyrepository.findById(id);
		return getcompanybyid;

	}
	public Company updatebyid(Company company,Long id)
	{
		 Optional<Company> companyid=companyrepository.findById(id);
		 if(companyid.isPresent())
		 {
			   Company existcompany= companyid.get();
			   existcompany.setCompanyname(company.getCompanyname());
			   existcompany.setCompanyaddress(company.getCompanyaddress());
			   existcompany.setCompanycountrycode(company.getCompanycountrycode());
			   existcompany.setCompanyphone(company.getCompanyphone());
			   existcompany.setCompanyemail(company.getCompanyemail());
			   existcompany.setCompanywebsite(company.getCompanywebsite());
			   existcompany.setCreatedby(company.getCreatedby());
			   existcompany.setCompanylogo(company.getCompanylogo());
			   existcompany.setStatus(company.isStatus());
			   existcompany.setModifiedby(company.getModifiedby());
			   existcompany.setModifieddate(company.getModifieddate());
			   existcompany.setIpaddress(company.getIpaddress());
			   existcompany.setIsdelete(company.isIsdelete());
			   existcompany.setParent_id(company.getParent_id());
			   return companyrepository.save(existcompany);
		 }
		 else {
		        // Handle case where company is not found
		        throw new EntityNotFoundException("Company with ID " + id + " not found.");
		    }

	}

//	public void deleteById(Long id) {
//	    // Check if the company exists
//	    Optional<Company> existingCompanyOptional = companyrepository.findById(id);
//
//	    if (existingCompanyOptional.isPresent()) {
//	        // Delete the company if it exists
//	        companyrepository.deleteById(id);
//	        
//	    } else {
//	        // Handle case where company is not found
//	        throw new EntityNotFoundException("Company with ID " + id + " not found.");
//	    }
//	}

	public String deleteCompany(Long id) {
		Company existingCompany = companyrepository.findById(id)
		 .orElseThrow(()   -> new RuntimeException("Company not found"));

		existingCompany.setCompanyname(existingCompany.getCompanyname());
		existingCompany.setCompanyaddress(existingCompany.getCompanyaddress());
		existingCompany.setCompanycountrycode(existingCompany.getCompanycountrycode());
		existingCompany.setCompanyphone(existingCompany.getCompanyphone());
		existingCompany.setCompanyemail(existingCompany.getCompanyemail());
		existingCompany.setCompanywebsite(existingCompany.getCompanywebsite());
		existingCompany.setCreatedby(existingCompany.getCreatedby());
		existingCompany.setCompanylogo(existingCompany.getCompanylogo());
		existingCompany.setStatus(existingCompany.isStatus());
		existingCompany.setModifiedby(existingCompany.getModifiedby());
		existingCompany.setModifieddate(existingCompany.getModifieddate());
		existingCompany.setIpaddress(existingCompany.getIpaddress());
		//existingCompany.setIsdelete(existingCompany.isIsdelete());
		existingCompany.setParent_id(existingCompany.getParent_id());
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

	


}

