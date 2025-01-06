package com.meld.techo.travel.crm.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
 
public class DesignationsDTO {

	private Long id;
    private String designationName;
    private String createdBy;
    private String modifiedBy;
    private String ipAddress;
    private boolean status;
    private boolean isdelete;
    private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
    private DepartmentsDTO department;
 
	public DesignationsDTO(Long id, String designationName, String createdBy, String modifiedBy, String ipAddress,
			boolean status, boolean isdelete, LocalDateTime createdDate, LocalDateTime modifiedDate,
			DepartmentsDTO department) {
		super();
		this.id = id;
		this.designationName = designationName;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.ipAddress = ipAddress;
		this.status = status;
		this.isdelete = isdelete;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.department = department;
	}
 
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	public String getDesignationName() {
		return designationName;
	}
 
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
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
 
	public String getIpAddress() {
		return ipAddress;
	}
 
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
 
	public DepartmentsDTO getDepartment() {
		return department;
	}
 
	public void setDepartment(DepartmentsDTO department) {
		this.department = department;
	}

 
}
