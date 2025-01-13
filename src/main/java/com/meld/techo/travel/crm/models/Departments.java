package com.meld.techo.travel.crm.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
 
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
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "departments_master")
@JsonIgnoreProperties(value= {"user"})
public class Departments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="departmentid")
	private Long id;
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Department name must only contain alphabetical characters and spaces.")
	@Column(name = "department_name")
	private String departmentName;
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="modified_by")

	private String modifiedBy;
	private String ipaddress;
	private boolean status;
	private boolean isdelete;
	
	@Column(name="created_date")

	private LocalDateTime createdDate;
	@Column(name="modified_date")
	private LocalDateTime modifiedDate;
	@JsonIgnore

@OneToMany(mappedBy = "departments", cascade = CascadeType.ALL,fetch = FetchType.LAZY)

	private Set<Designations> designations;

	@OneToMany(mappedBy="department",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<User> user;

	@PrePersist
    protected void onCreate() {
        this.createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
        this.modifiedBy = this.createdBy;
        createdDate = LocalDateTime.now();
		modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        modifiedDate = LocalDateTime.now();
        
    }
	

//	 private static final ThreadLocal<String> userIpAddress = new ThreadLocal<>();
//
//	    // Getters and setters...
//
//	    // This method will be used to set the IP address in the ThreadLocal variable
//	    public static void setUserIpAddress(String ipAddress) {
//	        userIpAddress.set(ipAddress);
//	    }
//
//	    // Getter for ipaddress
//	    public String getIpaddress() {
//	        return ipaddress;
//	    }
//
//	    // Setter for ipaddress
//	    public void setIpaddress(String ipaddress) {
//	        this.ipaddress = ipaddress;
//	    }
//
//	    @PrePersist
//	    protected void onCreate() {
//	        // Set createdBy and modifiedBy from the SecurityContext
//	        this.createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
//	        this.modifiedBy = this.createdBy;
//
//	        // Set created and modified date
//	        createdDate = LocalDateTime.now();
//	        modifiedDate = LocalDateTime.now();
//
//	        // Set the IP address from the ThreadLocal variable (if available)
//	        this.ipaddress = userIpAddress.get();
//	    }
//
//	    @PreUpdate
//	    protected void onUpdate() {
//	        // Set modifiedBy and modifiedDate during updates
//	        this.modifiedBy = SecurityContextHolder.getContext().getAuthentication().getName();
//	        modifiedDate = LocalDateTime.now();
//	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
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

		public Set<Designations> getDesignations() {
			return designations;
		}

		public void setDesignations(Set<Designations> designations) {
			this.designations = designations;
		}

		public List<User> getUser() {
			return user;
		}

		public void setUser(List<User> user) {
			this.user = user;
		}

		public String getIpaddress() {
			return ipaddress;
		}

		public void setIpaddress(String ipaddress) {
			this.ipaddress = ipaddress;
		}

		
	    
	    
	}