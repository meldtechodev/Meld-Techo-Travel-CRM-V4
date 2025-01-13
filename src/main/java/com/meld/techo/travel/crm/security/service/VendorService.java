package com.meld.techo.travel.crm.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meld.techo.travel.crm.dto.CustomException;
import com.meld.techo.travel.crm.models.Company;
import com.meld.techo.travel.crm.models.Vendor;
import com.meld.techo.travel.crm.repository.VendorRepository;

@Service
public class VendorService {
	
	
	@Autowired
	private VendorRepository vendorRepository;
	
	
//	public List<Vendor> getAllVendor(){
//		return vendorRepository.findAll();
//	}
	
	
	
	public Page<Vendor> getVendor(int page , int size , String sortDirection){
		Sort sort = Sort.by(Sort.Order.asc("vendorName"));
		
		if("desc".equalsIgnoreCase(sortDirection)) {
			sort = Sort.by(Sort.Order.desc("vendorName"));
		}		
		
		PageRequest  pageable = PageRequest.of(page, size, sort);
		return vendorRepository.findAll(pageable);
	}
	
	
	
	public Optional<Vendor> getVendorById(Long id){
		return vendorRepository.findById(id);
	}
	
	
	public Vendor addVendor(Vendor vendor) {
		return vendorRepository.save(vendor);
	}
	
	
	
	public Vendor updateVendor(Vendor v) {
		return vendorRepository.save(v);
	}
	
	
	public void deleteById(long id) {
		 vendorRepository.deleteById(id);
	}

	public Vendor findById(Long id) {
		return null;
		
	}



//	public boolean exitsByVendorEmail(String vendorEmail) {
//		return vendorRepository.exitsByVendorEmail(vendorEmail);
//	}



	public boolean existsByVendorEmail(String vendorEmail) {
		return vendorRepository.existsByVendorEmail(vendorEmail);
	}
	
	public boolean existsByVendorContactNo(String vendorContactNo) {
		return vendorRepository.existsByVendorContactNo(vendorContactNo);
	}
	


	public String deleteVendor(Long id) {
		Vendor existingVendor = vendorRepository.findById(id)
		            .orElseThrow(() -> new CustomException("Vendor not found", "404"));
		existingVendor.setIsdelete(true); 

		    
		vendorRepository.save(existingVendor);

		    return "data deleted"; 
		}





}
