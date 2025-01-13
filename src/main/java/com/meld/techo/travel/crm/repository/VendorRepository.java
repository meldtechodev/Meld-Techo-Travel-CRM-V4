package com.meld.techo.travel.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long>{
	
	boolean existsByVendorEmail(String vendorEmail);

	 boolean existsByVendorContactNo(String vendorContactNo);
	 @Query("SELECT v.vendorName FROM Vendor v WHERE v.id = :supplierId")
	    String findSupplierNameById(Long supplierId);
}