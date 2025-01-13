package com.meld.techo.travel.crm.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

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
import jakarta.validation.constraints.Size;

@Entity
@Table(name="city_master")
//@JsonIgnoreProperties(value = {  
//		})
public class City {
   
	private static final String code = null;
	 
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	
	@NotBlank(message="City is required")
	@Size(max=200,message = "City should not exceed 200 characters")
	@Column(name="city_Name")
	private String cityName;

	private String ipaddress;
 
    private boolean status;
	
	private LocalDateTime created_date;
	
	private LocalDateTime modified_date;
	
	private String created_by;
	
	private String modified_by;
	
	private boolean isdelete;


	//@Column(name="dimage")
	@Column(name = "dimage", columnDefinition = "JSON")
	@Convert(converter = ListToJsonConverter.class)
	private List< String> d_image;

	    @ManyToOne(fetch = FetchType.EAGER)
	    //@JoinColumn(name = "c_id", nullable = false)
	    @JoinColumn(name = "c_id")
	    private Country country;
	
	
	  // @NotBlank(message="keyofattractions is reuired")
	   private String keyofattractions;
 

	   @ManyToOne(fetch = FetchType.EAGER)
	    //@JoinColumn(name = "s_id", nullable = false)
	   @JoinColumn(name = "s_id")
	    private State state;
	   
	   
	   
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

		public String getCityName() {
			return cityName;
		}

		public void setCityName(String cityName) {
			this.cityName = cityName;
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

		public List<String> getD_image() {
			return d_image;
		}

		public void setD_image(List<String> d_image) {
			this.d_image = d_image;
		}

		public Country getCountry() {
			return country;
		}

		public void setCountry(Country country) {
			this.country = country;
		}

		public String getKeyofattractions() {
			return keyofattractions;
		}

		public void setKeyofattractions(String keyofattractions) {
			this.keyofattractions = keyofattractions;
		}

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public static String getCode() {
			return code;
		}

}
	