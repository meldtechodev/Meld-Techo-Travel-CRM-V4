package com.meld.techo.travel.crm.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;

//import com.MotherSon.CRM.models.Destination;
//import com.MotherSon.CRM.models.Hotel;
//import com.MotherSon.CRM.models.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Validated
@Table(name = "country_master")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// @JsonIgnoreProperties({"states", "destinations"})
//@JsonIgnoreProperties(value = { "states", "destinations","hotel" })
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "country name is required")
	@Size(min = 3, max = 100, message = "Country name must be between 3 and 100 characters")
	 @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Country name must contain only alphabets")
	@Column(name = "cname", nullable = false)
	private String countryName;

	@NotBlank(message = "country code name is required")
	@Pattern(regexp = "^[A-Z]{2,3}$", message = "Country code must consist of to uppercase letters and alphabatical order")
	@Column(name = "ccode", nullable = false, unique = true)
	private String code;
     
	@NotBlank(message="country phone pCode required")
	private String pcode;
	
//	@NotBlank(message = "Ip address is reqired")
//	@Column(name = "ipAddress", nullable = false)
	private String ipaddress;


	
	
	
	
//	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	@JsonManagedReference
//	private Set<State> states;

	

//	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonManagedReference
//	private Set<Destination> destinations;
//	
//	
//	@OneToMany(mappedBy = "country",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
//	@JsonManagedReference
//	private Set<Hotel> hotel;

//	 @ElementCollection
	@Column(name = "cimages", columnDefinition = "JSON")
	@Convert(converter = ListToJsonConverter.class)
	private List<String> cimage;

	

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public List<String> getCimage() {
		return cimage;
	}

	public void setCimage(List<String> cimage) {
		this.cimage = cimage;
	}
   
	//@NotBlank(message="status is required")
	//@NotNull(message = "Status is required")
	@Column(name = "status")
	private boolean status;

	//@NotBlank(message = "Created by is required.")
	@Column(name = "created_by")
	private String createdby;

	//@NotBlank(message = "Modified by is required.")
   @Column(name = "modified_by")
	private String modifiedby;

	private boolean isdelete;
	
	@Column(name="created_date")

	private LocalDateTime createddate;
    
	@Column(name="modified_date")
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

		public String getCountryName() {
			return countryName;
		}

		public void setCountryName(String countryName) {
			this.countryName = countryName;
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

		public boolean isIsdelete() {
			return isdelete;
		}

		public void setIsdelete(boolean isdelete) {
			this.isdelete = isdelete;
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

		public String getIpaddress() {
			return ipaddress;
		}

		public void setIpaddress(String ipaddress) {
			this.ipaddress = ipaddress;
		}

	


	}