package com.brookes.garage.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 * A Customer entity represents a customer of the garage
 */
@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstname;
	
	private String lastname;
	
	private String phone_number;
	
	@Column(length = 1000)
	private String address;
	
	private boolean deleted;
	

	@OneToMany(mappedBy = "customer")
	private final List<CustomersCar> cars = new ArrayList<CustomersCar>();
	
	
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

	public List<CustomersCar> getCars() {
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
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
