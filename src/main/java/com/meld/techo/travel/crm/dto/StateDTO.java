package com.meld.techo.travel.crm.dto;

import java.time.LocalDateTime;
import java.util.List;

public class StateDTO {
	
	
	private Long id;
    private String stateName;
    private String code;
    private boolean status;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;
    //private Long countryId;
    private List<String> simage;
    
    private Long countryId;
    private String countryName;
	public StateDTO(Long id, String stateName, String code, boolean status, LocalDateTime created_date,
			LocalDateTime modified_date, List<String> simage, Long countryId, String countryName) {
		super();
		this.id = id;
		this.stateName = stateName;
		this.code = code;
		this.status = status;
		this.created_date = created_date;
		this.modified_date = modified_date;
		this.simage = simage;
		this.countryId = countryId;
		this.countryName = countryName;
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
	public List<String> getSimage() {
		return simage;
	}
	public void setSimage(List<String> simage) {
		this.simage = simage;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
    
    
    
}