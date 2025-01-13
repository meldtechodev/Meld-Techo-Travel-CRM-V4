package com.meld.techo.travel.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	
	boolean existsByEmailId(String emailId);
    boolean existsByContactNo(String contactNo);
    boolean existsByAdharno(String adharno);
    boolean existsByPassportId(String passportId);
    
    
    
	Customer findByContactNo(String contactNo);
	
	
	@Query("SELECT c FROM Customer c WHERE c.user.id = :userId")
    List<Customer> findByUserId(@Param("userId") Long userId);
}
	
