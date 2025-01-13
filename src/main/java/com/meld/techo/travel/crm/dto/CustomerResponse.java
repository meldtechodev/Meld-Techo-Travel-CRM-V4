package com.meld.techo.travel.crm.dto;



public class CustomerResponse {
	
	
	
	private String message;
    private CustomerResponse customer;

    public CustomerResponse(String message, CustomerResponse customer) {
        this.message = message;
        this.customer = customer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }
}


