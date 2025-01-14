package com.meld.techo.travel.crm.models;

import java.time.LocalDateTime;
//import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Table(name="company_master")
@Entity
@JsonIgnoreProperties(value= {"user"})
public class Company {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@Column(name="companyid")
	private Long id;
	@NotNull(message = "Company name is required")
  @Size(min = 3, max = 100, message = "Company name must be between 3 and 100 characters")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Company name must only contain alphabets and spaces")
	@Column(name="company_name")
	private String companyname;
	@Column(nullable=true)
	private Long parent_id;
	@NotNull(message = "Company address is required")
	@Size(min = 3, max = 255, message = "Company address must be between 5 and 255 characters")
	@Column(name="company_address")
	private String companyaddress;
	 @Email(message = "Invalid email format")
	  @Size(max = 100, message = "Email must not exceed 150 characters")
	@Column(name="company_email")
	private String companyemail;
	 @Pattern(regexp = "^[A-Za-z\\s]+\\(\\+\\d+\\)$", message = "Invalid country code format. Example: India(+91)")
	 @Size(max = 20, message = "Companycountrycode must not exceed 20 characters")
	@Column(name="company_country_code")
	private String companycountrycode;
	 @Pattern(regexp = "^[0-9]*$", message = "Company phone number must be numerical only")
	 @Size(min = 10, max = 15, message = "Company phone number must be between 10 and 15 digits")
	@Column(name="company_phone")
	private String companyphone;
@Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$", message = "Company website must be a valid URL")
	@Column(name="company_website")
	private String companywebsite;

	@Column(name = "company_logo_path")
//	@Convert(converter = ListToJsonConverter.class)
	private List<String> companylogo;
	
	private String ipaddress;

	private boolean status;
	private boolean isdelete;

	@JsonManagedReference
	@OneToMany( mappedBy="company",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<User> user;

	@Column(name="created_by")
	private String createdby;
	@Column(name="modified_by")
	private String modifiedby;
	@Column(name="created_date")
	private LocalDateTime createddate;
	@Column(name="modified_date")
	private LocalDateTime modifieddate;
	

	
	 private static final ThreadLocal<String> userIpAddress = new ThreadLocal<>();

	    public static void setUserIpAddress(String ipAddress) {
	        userIpAddress.set(ipAddress);
	    }

	   
	 
	
	
	    @PrePersist
	    protected void onCreate() {
	        // Set createdBy and modifiedBy from the SecurityContext
	        this.createdby = SecurityContextHolder.getContext().getAuthentication().getName();
	        this.modifiedby = this.createdby;

	        // Set created and modified date
	        createddate = LocalDateTime.now();
	        modifieddate = LocalDateTime.now();

	        // Set the IP address from the ThreadLocal variable (if available)
	        this.ipaddress = userIpAddress.get();
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        // Set modifiedBy and modifiedDate during updates
	        this.modifiedby = SecurityContextHolder.getContext().getAuthentication().getName();
	        modifieddate = LocalDateTime.now();
	    }

	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public List<String> getCompanylogo() {
		return companylogo;
	}

	public void setCompanylogo(List<String> imageUrls) {
		this.companylogo = imageUrls;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompanyaddress() {
		return companyaddress;
	}
	public void setCompanyaddress(String companyaddress) {
		this.companyaddress = companyaddress;
	}
	public String getCompanyemail() {
		return companyemail;
	}
	public void setCompanyemail(String companyemail) {
		this.companyemail = companyemail;
	}
	public String getCompanycountrycode() {
		return companycountrycode;
	}
	public void setCompanycountrycode(String companycountrycode) {
		this.companycountrycode = companycountrycode;
	}
	public String getCompanyphone() {
		return companyphone;
	}
	public void setCompanyphone(String companyphone) {
		this.companyphone = companyphone;
	}
	public String getCompanywebsite() {
		return companywebsite;
	}
	public void setCompanywebsite(String companywebsite) {
		this.companywebsite = companywebsite;
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


	public List<User> getUser() {
		return user;
	}


	public void setUser(List<User> user) {
		this.user = user;
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





	public static ThreadLocal<String> getUseripaddress() {
		return userIpAddress;
	}



}