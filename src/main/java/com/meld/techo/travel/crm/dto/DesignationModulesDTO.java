package com.meld.techo.travel.crm.dto;

public class DesignationModulesDTO {
	
	
	private Long id;
	private String createdBy;
	private String ipaddress;
	private boolean status;
	
	private Long designationsId;
	private String designationName;
	
	private Long modulesId;
	private String moduleName;
	public DesignationModulesDTO(Long id, String createdBy, String ipaddress, boolean status, Long designationsId,
			String designationName, Long modulesId, String moduleName) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.ipaddress = ipaddress;
		this.status = status;
		this.designationsId = designationsId;
		this.designationName = designationName;
		this.modulesId = modulesId;
		this.moduleName = moduleName;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public Long getDesignationsId() {
		return designationsId;
	}
	public void setDesignationId(Long designationsId) {
		this.designationsId = designationsId;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public Long getModulesId() {
		return modulesId;
	}
	public void setModuleId(Long modulesId) {
		this.modulesId = modulesId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	

}
