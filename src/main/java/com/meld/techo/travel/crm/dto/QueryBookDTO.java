package com.meld.techo.travel.crm.dto;

import java.time.LocalDateTime;

public class QueryBookDTO {
	
	private Long id;
	private String proposalId;
	private String requirementType;
	private LocalDateTime travelDate;
	private int days;
	private int nights;
	private int totalTravellers;
	private int adults;
	private int kids;
	private int infants;
	private String salutation;
	private String fname;
	private String lname;
	private String emailId;
	private String contactNo;
	private String leadSource;
	private String foodPreferences;
	private double basicCost;
	private double gst;
	private double totalCost;
	private LocalDateTime query_Date;
	private String queryType;
	private String queryCreatedFrom;
	private boolean emailStatus;
	private boolean leadStatus;
	private LocalDateTime lastUpdated_Date;
	private String ipAddress;
	private boolean isdelete;
	
//	private Long packId;
//	private String pkName;
	
	private Long cityId;
	private String cityName;
	
	private Long customerId;
	private String fName;
	private String lName;
	//private String contactNo1;
	
	
	
	private Long userId;
	private String name;
	private String mname;
	private String lname1;
	private String mobnumber;
	public QueryBookDTO(Long id, String proposalId, String requirementType, LocalDateTime travelDate, int days,
			int nights, int totalTravellers, int adults, int kids, int infants, String salutation, String fname,
			String lname, String emailId, String contactNo, String leadSource, String foodPreferences, double basicCost,
			double gst, double totalCost, LocalDateTime query_Date, String queryType, String queryCreatedFrom,
			boolean emailStatus, boolean leadStatus, LocalDateTime lastUpdated_Date, String ipAddress, boolean isdelete,
			Long cityId, String cityName, Long customerId, String fName2, String lName2, Long userId, String name,
			String mname, String lname1, String mobnumber) {
		super();
		this.id = id;
		this.proposalId = proposalId;
		this.requirementType = requirementType;
		this.travelDate = travelDate;
		this.days = days;
		this.nights = nights;
		this.totalTravellers = totalTravellers;
		this.adults = adults;
		this.kids = kids;
		this.infants = infants;
		this.salutation = salutation;
		this.fname = fname;
		this.lname = lname;
		this.emailId = emailId;
		this.contactNo = contactNo;
		this.leadSource = leadSource;
		this.foodPreferences = foodPreferences;
		this.basicCost = basicCost;
		this.gst = gst;
		this.totalCost = totalCost;
		this.query_Date = query_Date;
		this.queryType = queryType;
		this.queryCreatedFrom = queryCreatedFrom;
		this.emailStatus = emailStatus;
		this.leadStatus = leadStatus;
		this.lastUpdated_Date = lastUpdated_Date;
		this.ipAddress = ipAddress;
		this.isdelete = isdelete;
		this.cityId = cityId;
		this.cityName = cityName;
		this.customerId = customerId;
		fName = fName2;
		lName = lName2;
		this.userId = userId;
		this.name = name;
		this.mname = mname;
		this.lname1 = lname1;
		this.mobnumber = mobnumber;
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
	public LocalDateTime getQuery_Date() {
		return query_Date;
	}
	public void setQuery_Date(LocalDateTime query_Date) {
		this.query_Date = query_Date;
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
	public LocalDateTime getLastUpdated_Date() {
		return lastUpdated_Date;
	}
	public void setLastUpdated_Date(LocalDateTime lastUpdated_Date) {
		this.lastUpdated_Date = lastUpdated_Date;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public boolean isIsdelete() {
		return isdelete;
	}
	public void setIsdelete(boolean isdelete) {
		this.isdelete = isdelete;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getLname1() {
		return lname1;
	}
	public void setLname1(String lname1) {
		this.lname1 = lname1;
	}
	public String getMobnumber() {
		return mobnumber;
	}
	public void setMobnumber(String mobnumber) {
		this.mobnumber = mobnumber;
	}
	
	
	
}