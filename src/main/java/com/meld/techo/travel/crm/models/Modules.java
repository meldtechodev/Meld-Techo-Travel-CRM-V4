package com.meld.techo.travel.crm.models;

import java.time.LocalDateTime;
import java.util.Set;
 
import com.fasterxml.jackson.annotation.JsonIgnore;
 
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
 
@Entity
@Table(name = "modules_Master")
public class Modules {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "parent_id")
	private Long parentId;
	@NotNull(message = "Module name cannot be null.")
	
	@Column(name = "module_name")
	private String moduleName;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@Column(name = "ipaddress")
	private String ipaddress;

	private boolean status;
	private boolean isdelete;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;


	@PrePersist
	protected void onCreate() {
		createdDate = LocalDateTime.now();
		modifiedDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		modifiedDate = LocalDateTime.now();
	}
 
 
	public Long getId() {
		return id;
	}
 
 
	public void setId(Long id) {
		this.id = id;
	}
 
 
	public Long getParentId() {
		return parentId;
	}
 
 
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
 
 
	public String getModuleName() {
		return moduleName;
	}
 
 
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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
 



 
}