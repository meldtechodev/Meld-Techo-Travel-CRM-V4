package com.meld.techo.travel.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meld.techo.travel.crm.models.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

}