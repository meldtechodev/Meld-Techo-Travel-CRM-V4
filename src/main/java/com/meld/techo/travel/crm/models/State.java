package com.meld.techo.travel.crm.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIgnoreProperties(value = {  "hotel"
})
@Table(name = "state_master")


public class State {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "state name is required")
	@Size(min = 2, max = 30, message = "state name must be between 2 and 30 characters")
	 @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "state name must contain  alphabets")

	@Column(name = "sname", nullable = false)
	private String stateName;

	@NotBlank(message = "State code is required")
	@Pattern(regexp = "^[A-Z]{2,3}$", message = "State code must be 2 to 3 uppercase letters")
	@Column(name="scode")
	private String code;
	
	private boolean status;

    private LocalDateTime created_date;
	
	private LocalDateTime modified_date;
	
	private String created_by;
	
	private String modified_by;
	
	private boolean isdelete;

	private String ipaddress;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cid",nullable = false)
	//@JoinColumn(name = "cid")
	//@JsonBackReference
	private Country country;

	@Column(name = "simage", columnDefinition = "JSON")
	@Convert(converter = ListToJsonConverter.class)
	 //@Column(name="simage")
	private List<String> simage;

	
	 
	 
	 
	 @PrePersist
	    protected void onCreate() {
	        this.created_by = SecurityContextHolder.getContext().getAuthentication().getName();
	        this.modified_by = this.created_by;
	        created_date = LocalDateTime.now();
			modified_date = LocalDateTime.now();
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        this.modified_by = SecurityContextHolder.getContext().getAuthentication().getName();
	        modified_date = LocalDateTime.now();
	        
	    }
	 
	 
	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDateTime getCreated_date() {
		return created_date;
	}

	public void setCreated_date(LocalDateTime created_date) {
		this.created_date = created_date;
	}

	public LocalDateTime getModified_date() {
		return modified_date;
	}

	public void setModified_date(LocalDateTime modified_date) {
		this.modified_date = modified_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	public boolean isIsdelete() {
		return isdelete;
	}

	public void setIsdelete(boolean isdelete) {
		this.isdelete = isdelete;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<String> getSimage() {
		return simage;
	}

	public void setSimage(List<String> simage) {
		this.simage = simage;
	}

	


}
