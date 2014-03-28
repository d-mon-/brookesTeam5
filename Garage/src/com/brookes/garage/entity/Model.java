package com.brookes.garage.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * A Model entity represents a specific car model from a car brand
 */
@Entity
public class Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	private Brand brand;
	
	private boolean delete_flag = false;

	@OneToMany(mappedBy = "model")
	private final List<Part> parts = new ArrayList<Part>();
	
	@OneToMany(mappedBy = "model")
	private final List<CustomersCar> cars = new ArrayList<CustomersCar>();

	
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

	public List<CustomersCar> getCars() {
		return cars;
	}
	
	@Override
	public String toString() {
		return brand.getName() + " " + name;
	}

	public boolean isDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(boolean delete_flag) {
		this.delete_flag = delete_flag;
	}
}