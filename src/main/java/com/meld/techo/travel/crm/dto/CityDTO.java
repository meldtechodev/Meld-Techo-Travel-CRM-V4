package com.meld.techo.travel.crm.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CityDTO {
	
	private Long id;
	private String cityName;
	private boolean status;
	private LocalDateTime created_date;
	private LocalDateTime modified_date;
	private String keyofattractions;
	private List< String> d_image;
	
	
	private Long countryId;
	private String countryName;
	
	private Long stateId;
	private String stateName;
	public CityDTO(Long id, String cityName, boolean status, LocalDateTime created_date, LocalDateTime modified_date,
			String keyofattractions, List<String> d_image, Long countryId, String countryName, Long stateId,
			String stateName) {
		super();
		this.id = id;
		this.cityName = cityName;
		this.status = status;
		this.created_date = created_date;
		this.modified_date = modified_date;
		this.keyofattractions = keyofattractions;
		this.d_image = d_image;
		this.countryId = countryId;
		this.countryName = countryName;
		this.stateId = stateId;
		this.stateName = stateName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
	public String getKeyofattractions() {
		return keyofattractions;
	}
	public void setKeyofattractions(String keyofattractions) {
		this.keyofattractions = keyofattractions;
	}
	public List<String> getD_image() {
		return d_image;
	}
	public void setD_image(List<String> d_image) {
		this.d_image = d_image;
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
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
	