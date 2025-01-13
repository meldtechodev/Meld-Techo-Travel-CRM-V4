package com.meld.techo.travel.crm.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.meld.techo.travel.crm.dto.DepartmentsDTO;
import com.meld.techo.travel.crm.dto.DesignationsDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
@Entity
@Table(name = "designations_master")
@JsonIgnoreProperties(value= {"user"})
public class Designations {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Pattern(regexp = "^[A-Za-z ]+$", message = "Designation name must only contain alphabetical characters and spaces.")
	@Column(name = "designation_name", nullable = false)
	private String designationName;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String modifiedBy;
	private String ipaddress;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id",nullable = true)
	private Departments departments;



	@JsonManagedReference
	@OneToOne(mappedBy="designation",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private User user;
	
	private boolean status;
	private boolean isdelete;
	

	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

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
	public Departments getDepartments() {
		return departments;
	}
	public void setDepartments(Departments departments) {
		this.departments = departments;
	}

	public User getUser() {
		return user;
	}
 
	public void setUser(User user) {
		this.user = user;
	}

}
 
	
//	public DesignationsDTO toDTO() {
//        DepartmentsDTO departmentDTO = (departments != null) ? new DepartmentsDTO(departments.getId(), departments.getDepartmentName()) : null;
//        return new DesignationsDTO(
//                this.id,
//                this.designationName,
//                this.createdBy,
//                this.modifiedBy,
//                this.ipaddress,
//                this.status,
//                this.isdelete,
//                this.createdDate,
//                this.modifiedDate,
//                departmentDTO
//        );
//    }
//}

 