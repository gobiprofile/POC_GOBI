package com.gre.learn.dto;


public class Customer {

	private Long customerId;
	private String customerName;
	private String email;
	
	public Customer(Long customerId, String customerName, String email) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.email = email;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public String getEmail() {
		return email;
	}
	
}
