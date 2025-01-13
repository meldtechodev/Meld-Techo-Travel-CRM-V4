package com.meld.techo.travel.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.QueryBook;

@Repository
public interface QueryBookRepository extends JpaRepository<QueryBook, Long> {

	
	
	List<QueryBook> findAll();

	@Query("SELECT q FROM QueryBook q WHERE q.userid.id = :userId")
	List<QueryBook> findByUserId(@Param("userId") Long userId);


	List<QueryBook> findByCustomerId(Long customerId);

	//List<QueryBook> findByDCityId(Long cityId);

	List<QueryBook> findByCityId(Long cityId);
	
}