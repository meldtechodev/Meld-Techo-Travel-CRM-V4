package com.meld.techo.travel.crm.dto;


public class DepartmentsDTO {
	
	private Long id;
   private String departmentName;
   
   
	public DepartmentsDTO(Long id, String departmentName) {
		super();
		this.id = id;
		this.departmentName = departmentName;
	}


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
   
   

}


