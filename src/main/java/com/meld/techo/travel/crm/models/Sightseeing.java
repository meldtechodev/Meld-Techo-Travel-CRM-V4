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
@Table(name = "sightseeing_master")
public class Sightseeing {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotBlank(message = "Title is required")
	@Column(name = "title")
	private String title;
	
	private String ipaddress;
	
	//@Column(name = "status" , nullable = false)
	private boolean status;
	
	private boolean isdelete;
	
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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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