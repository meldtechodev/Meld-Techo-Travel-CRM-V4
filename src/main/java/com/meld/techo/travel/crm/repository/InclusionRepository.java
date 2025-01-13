package com.meld.techo.travel.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.Inclusion;

@Repository
public interface InclusionRepository extends JpaRepository<Inclusion,Long> {
    
}