package com.meld.techo.travel.crm.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.persistence.Column;
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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
//@JsonInclude(JsonInclude.Include.NON_NULL)

@Table(name = "query_master")
public class QueryBook {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	private String proposalId;
	
	@NotBlank(message = "requirementType is required")
	@Column(name = "requirement_type")
	private String requirementType;
	
	 @Column(name = "travel_Date")
	 private LocalDateTime travelDate;
	
	 private int days;
	
	 private int nights;
	
	 
	 @Min(value = 1, message = "Total Travellers must be at least 1")
	 @Max(value = 500, message = "Total Travellers cannot exceed 500")
	 @Column(name = "total_Travellers")
	 private int totalTravellers;
	
	 
	 @Min(value = 0, message = "Adults cannot be negative")
	 private int adults;
	
	 @Min(value = 0, message = "Kids cannot be negative")
	 private int kids;
	
	 @Min(value = 0, message = "Infants cannot be negative")
	 private int infants;
	
	 @Column(name="packid")
	 private Long packid;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "cityid")
	//@JsonBackReference
	private City city;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "fromcityid")
	private City fromcityid;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "custid")
	//@JsonBackReference
	private Customer customer;
	
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(
//	    name = "query_customer", 
//	    joinColumns = @JoinColumn(name = "query_id"), 
//	    inverseJoinColumns = @JoinColumn(name = "customer_id")
//	)
//
//	
//	private List<Customer> customer = new ArrayList<>();


	@NotBlank(message = "Salutation is required")
	@Column(name = "salutation")
	private String salutation;
	
	@NotBlank(message = "Fname name is required")
	@Column(name = "f_name")
	private String fname;
	
	@NotBlank(message = "Last name is required")
	@Column(name = "l_name")
	private String lname;
	
	@NotBlank(message = "Email id is required")
	@Email(message = "Email ID must be a valid email address")
	@Column(name = "Email_id")
	private String emailId;
	
	@NotBlank(message = "Contact No is required")
	@Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Contact No must be a valid phone number")
	@Column(name = "contact_No")
	private String contactNo;
	
	@NotBlank(message = "leadSource is required")
	@Column(name = "lead_Source")
	private String leadSource;
	
	@Column(name = "food_Preferences")
	private String foodPreferences;
	
	@PositiveOrZero(message = "Basic Cost cannot be negative")
	@Column(name = "basic_Cost")
	private double basicCost;
	
	@PositiveOrZero(message = "GST cannot be negative")
	private double gst;
	
	@PositiveOrZero(message = "Total Cost cannot be negative")
	@Column(name = "total_Cost")
	private double totalCost;
	
	@Column(name = "query_Type")
	private String queryType;
	
	@Column(name = "query_CreatedFrom")
	private String queryCreatedFrom;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	//@JsonBackReference
	private User userid;
	
	@Column(name = "email_Status")
	private boolean emailStatus;
	
	@Column(name = "lead_Status")
	private boolean leadStatus;
	
	@Column(name = "query_createby")
	private String query_createby;
	
	@Column(name = "query_modifiedby")
	private String query_modifiedby;
	 
	@Column(name = "query_Date")
	private LocalDateTime query_Date;
	
	@Column(name = "last_Updated_Date")
	private LocalDateTime lastUpdated_Date;
	
	
	
  private String ipaddress;
  
  private boolean isdelete;
	
	
//	@PrePersist
//	protected void onCreate() {
//		
//		
//		 if (proposalId == null || proposalId.isEmpty()) {
//		        proposalId = "PROPOSAL-" + UUID.randomUUID().toString();  // Or any other custom logic
//		    }
//		 
//		query_Date = LocalDateTime.now();
//		lastUpdated_Date = LocalDateTime.now();
//	}
//	
//	@PreUpdate
//	protected void onUpdate() {
//		lastUpdated_Date = LocalDateTime.now();
//		
//	}
  
  
  @PrePersist
  protected void onCreate() {

if (proposalId == null || proposalId.isEmpty()) {
          proposalId = "PROPOSAL-" + UUID.randomUUID().toString();  // Or any other custom logic
      }
      this.query_createby = SecurityContextHolder.getContext().getAuthentication().getName();
      this.query_modifiedby = this.query_createby;
      query_Date = LocalDateTime.now();
		lastUpdated_Date = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
      this.query_modifiedby = SecurityContextHolder.getContext().getAuthentication().getName();
      lastUpdated_Date = LocalDateTime.now();
      
  }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	public String getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}

	public LocalDateTime getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(LocalDateTime travelDate) {
		this.travelDate = travelDate;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public int getTotalTravellers() {
		return totalTravellers;
	}

	public void setTotalTravellers(int totalTravellers) {
		this.totalTravellers = totalTravellers;
	}

	public int getAdults() {
		return adults;
	}

	public void setAdults(int adults) {
		this.adults = adults;
	}

	public int getKids() {
		return kids;
	}

	public void setKids(int kids) {
		this.kids = kids;
	}

	public int getInfants() {
		return infants;
	}

	public void setInfants(int infants) {
		this.infants = infants;
	}

	public Long getPackid() {
		return packid;
	}

	public void setPackid(Long packid) {
		this.packid = packid;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public City getFromcityid() {
		return fromcityid;
	}

	public void setFromcityid(City fromcityid) {
		this.fromcityid = fromcityid;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getFoodPreferences() {
		return foodPreferences;
	}

	public void setFoodPreferences(String foodPreferences) {
		this.foodPreferences = foodPreferences;
	}

	public double getBasicCost() {
		return basicCost;
	}

	public void setBasicCost(double basicCost) {
		this.basicCost = basicCost;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryCreatedFrom() {
		return queryCreatedFrom;
	}

	public void setQueryCreatedFrom(String queryCreatedFrom) {
		this.queryCreatedFrom = queryCreatedFrom;
	}

	public User getUserid() {
		return userid;
	}

	public void setUserid(User userid) {
		this.userid = userid;
	}

	public boolean isEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(boolean emailStatus) {
		this.emailStatus = emailStatus;
	}

	public boolean isLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(boolean leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getQuery_createby() {
		return query_createby;
	}

	public void setQuery_createby(String query_createby) {
		this.query_createby = query_createby;
	}

	public String getQuery_modifiedby() {
		return query_modifiedby;
	}

	public void setQuery_modifiedby(String query_modifiedby) {
		this.query_modifiedby = query_modifiedby;
	}

	public LocalDateTime getQuery_Date() {
		return query_Date;
	}

	public void setQuery_Date(LocalDateTime query_Date) {
		this.query_Date = query_Date;
	}

	public LocalDateTime getLastUpdated_Date() {
		return lastUpdated_Date;
	}

	public void setLastUpdated_Date(LocalDateTime lastUpdated_Date) {
		this.lastUpdated_Date = lastUpdated_Date;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public boolean isIsdelete() {
		return isdelete;
	}

	public void setIsdelete(boolean isdelete) {
		this.isdelete = isdelete;
	}
	
	
	
	
}
	