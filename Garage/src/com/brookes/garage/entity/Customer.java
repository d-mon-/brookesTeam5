package com.brookes.garage.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstname;
	
	private String lastname;
	
	private String phone_number;
	
	private String address;
	
	@OneToMany(mappedBy = "customer")
	private final List<Customers_car> cars = new ArrayList<Customers_car>();
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getPhone_number() {
		return phone_number;
	}
	
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public List<Customers_car> getCars() {
		return cars;
	}

	@Override
	public String toString() {
		return firstname + " " + lastname;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Customer)) {
			return false;
		}
	  
		Customer cus = (Customer) obj;
	  
		if (this.getId().equals(cus.getId())) {
			return true;
		}
	  
		return false;
	}
}
