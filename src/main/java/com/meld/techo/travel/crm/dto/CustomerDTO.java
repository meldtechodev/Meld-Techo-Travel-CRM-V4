package com.meld.techo.travel.crm.dto;

import java.time.LocalDateTime;

public class CustomerDTO {
	
	private Long Id;
	private String salutation;
	private String fName;
	private String lName;
	private String emailId;
	private String 	contactNo;
	private String marritalStatus;
	private String customerType;
	private String leadSource;
	private String adharno;
	private String passportId;
	private String createdby;
	private String modifiedby;
	private String ipaddress;
	private Boolean status;
	private boolean isdelete;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	
	
	private Long userId;
	private String name;
	private String mname;
	private String lname;
	public CustomerDTO(Long id, String salutation, String fName, String lName, String emailId, String contactNo,
			String marritalStatus, String customerType, String leadSource, String adharno, String passportId,
			String createdby, String modifiedby, String ipaddress, Boolean status, boolean isdelete,
			LocalDateTime createdDate, LocalDateTime modifiedDate, Long userId, String name, String mname,
			String lname2) {
		super();
		Id = id;
		this.salutation = salutation;
		this.fName = fName;
		this.lName = lName;
		this.emailId = emailId;
		this.contactNo = contactNo;
		this.marritalStatus = marritalStatus;
		this.customerType = customerType;
		this.leadSource = leadSource;
		this.adharno = adharno;
		this.passportId = passportId;
		this.createdby = createdby;
		this.modifiedby = modifiedby;
		this.ipaddress = ipaddress;
		this.status = status;
		this.isdelete = isdelete;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.userId = userId;
		this.name = name;
		this.mname = mname;
		lname = lname2;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
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
	public String getMarritalStatus() {
		return marritalStatus;
	}
	public void setMarritalStatus(String marritalStatus) {
		this.marritalStatus = marritalStatus;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getLeadSource() {
		return leadSource;
	}
	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}
	public String getAdharno() {
		return adharno;
	}
	public void setAdharno(String adharno) {
		this.adharno = adharno;
	}
	public String getPassportId() {
		return passportId;
	}
	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
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
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	
	
	
}