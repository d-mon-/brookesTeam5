package com.brookes.garage.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Repair {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String identifier;
	
    @Temporal(TemporalType.DATE)
	private Date creation_date;
	
	private int status;
	
	private String description;
	
	private Customers_car car;
	
	@OneToMany(mappedBy = "repair")
	private final List<Estimate> estimates = new ArrayList<Estimate>();

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Customers_car getCar() {
		return car;
	}

	public void setCar(Customers_car car) {
		this.car = car;
	}

	public List<Estimate> getEstimates() {
		return estimates;
	}
}