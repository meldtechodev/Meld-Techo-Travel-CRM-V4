package com.meld.techo.travel.crm.models;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "designation_modules_master")
public class DesignationModules {

	@Id
 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 
 
	private Long id;
 
	@Column(name = "created_by")
 
	private String createdBy;
 
	@Column(name = "modified_by")
 
	private String modifiedBy;
 
	private String ipaddress;
 
	private boolean status;
 
	private boolean isdelete;
 
	@Column(name = "created_date")
 
	private LocalDateTime createdDate;
 
	@Column(name = "modified_date")
 
	private LocalDateTime modifiedDate;
 
 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "designations_id")
	private Designations designations;
 
 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "modules_id")
	private Modules modules;
 
 
	@PrePersist
    protected void onCreate() {
        this.createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
        this.modifiedBy = this.createdBy;
        LocalDateTime createddate = LocalDateTime.now();
		modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        modifiedDate = LocalDateTime.now();
        
    }
 
 
	public Long getId() {
		return id;
	}
 
 
	public void setId(Long id) {
		this.id = id;
	}
 
 
	public String getCreatedBy() {
		return createdBy;
	}
 
 
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
 
 
	public String getModifiedBy() {
		return modifiedBy;
	}
 
 
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
 
 
	public String getIpaddress() {
		return ipaddress;
	}
 
 
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
 
 
	public boolean isStatus() {
		return status;
	}
 
 
	public void setStatus(boolean status) {
		this.status = status;
	}
 
 
	public boolean isIsdelete() {
		return isdelete;
	}
 
 
	public void setIsdelete(boolean isdelete) {
		this.isdelete = isdelete;
	}
 
 
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
 
 
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
 
 
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
 
 
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
 
 
	
 
 
	public Modules getModules() {
		return modules;
	}
 
 
	public void setModules(Modules modules) {
		this.modules = modules;
	}
 
 
	
 
 
	public Designations getDesignations() {
		return designations;
	}
 
 
	public void setDesignations(Designations designations) {
		this.designations = designations;
	}
 
 
//	public void setDesignations(DesignationsDTO designation) {
//		// TODO Auto-generated method stub
//		
//	}

}