package com.brookes.garage.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * A CustomersCar entity represents a car (with a specific model and plate number) owned by a customer
 */
@Entity
public class CustomersCar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String number_plate;
	
	private Customer customer;
	
	private Model model;
	
	@OneToMany(mappedBy = "car")
	private final List<Repair> repairs = new ArrayList<Repair>();

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber_plate() {
		return number_plate;
	}

	public void setNumber_plate(String number_plate) {
		this.number_plate = number_plate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public List<Repair> getRepairs() {
		return repairs;
	}

	@Override
	public String toString() {
		return model.toString() + " " + number_plate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof CustomersCar)) {
			return false;
		}
	  
		CustomersCar cus = (CustomersCar) obj;
	  
		if (this.getId().equals(cus.getId())) {
			return true;
		}
	  
		return false;
	}
}
