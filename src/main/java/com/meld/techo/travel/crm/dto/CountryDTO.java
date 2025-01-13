package com.meld.techo.travel.crm.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CountryDTO {
	
	private Long id;
    private String country_name;
	private String country_code;
	private String pcode;
	private boolean status;
	private List<String> image;
	private LocalDateTime created_date;
	
	public CountryDTO(Long id, String country_name, String country_code, String pcode, boolean status,
			List<String> image, LocalDateTime created_date) {
		super();
		this.id = id;
		this.country_name = country_name;
		this.country_code = country_code;
		this.pcode = pcode;
		this.status = status;
		this.image = image;
		this.created_date = created_date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
	}
	public LocalDateTime getCreated_date() {
		return created_date;
	}
	public void setCreated_date(LocalDateTime created_date) {
		this.created_date = created_date;
	}
	
	
	
}