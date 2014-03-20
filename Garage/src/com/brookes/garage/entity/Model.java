package com.brookes.garage.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	private Brand brand;

	@OneToMany(mappedBy = "model")
	private final List<Part> parts = new ArrayList<Part>();
	
	@OneToMany(mappedBy = "model")
	private final List<Customers_car> cars = new ArrayList<Customers_car>();

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<Part> getParts() {
		return parts;
	}

	public List<Customers_car> getCars() {
		return cars;
	}
	
}