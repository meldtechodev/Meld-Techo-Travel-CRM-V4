package com.meld.techo.travel.crm.models;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "vendor_master")
public class Vendor {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotBlank(message = "Vendor Name is required")
	@Column(name = "vendor_Name" )
	private String vendorName;
	
	@NotBlank(message = "Vendor Email id required is ")
	@Column(name = "vendor_Email_id")
	private String vendorEmail;
	
	@NotBlank(message = "Vendor Contact Number is required")
	@Column(name = "vendor_Contact_No")
	private String vendorContactNo;
	
	@NotBlank(message = "Vendor Address is required ")
	@Column(name = "vendor_Address")
	private String vendorAddress;
	
	public String ipaddress;
	
	public boolean status;
	
	public boolean isdelete;
	
	@Column(name = "created_by")
	private String createdby;
	
	@Column(name = "modified_by")
	private String modifiedby;
	
	@Column(name = "created_date")
	private LocalDateTime createddate;
	
	@Column(name = "modified_date")
	private LocalDateTime modifieddate;
	
	@PrePersist
    protected void onCreate() {
        this.createdby = SecurityContextHolder.getContext().getAuthentication().getName();
        this.modifiedby = this.createdby;
        createddate = LocalDateTime.now();
		modifieddate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedby = SecurityContextHolder.getContext().getAuthentication().getName();
        modifieddate = LocalDateTime.now();
        
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorEmail() {
		return vendorEmail;
	}

	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}

	public String getVendorContactNo() {
		return vendorContactNo;
	}

	public void setVendorContactNo(String vendorContactNo) {
		this.vendorContactNo = vendorContactNo;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
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

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public LocalDateTime getCreateddate() {
		return createddate;
	}

	public void setCreateddate(LocalDateTime createddate) {
		this.createddate = createddate;
	}

	public LocalDateTime getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(LocalDateTime modifieddate) {
		this.modifieddate = modifieddate;
	}
	
}
