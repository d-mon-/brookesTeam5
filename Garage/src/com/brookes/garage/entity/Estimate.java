package com.brookes.garage.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * An Estimate entity is linked to a repair and to the requested car parts
 */
@Entity
public class Estimate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String identifier;
	
	private boolean invalidated;
	
    @Temporal(TemporalType.DATE)
	private Date creation_date;
	
	private Repair repair;
	
	@OneToMany(mappedBy = "estimate")
	private List<RequestedPart> parts = new ArrayList<RequestedPart>();

	@OneToOne(mappedBy = "estimate")
	private Invoice invoice;

	
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

	public boolean isInvalidated() {
		return invalidated;
	}

	public void setInvalidated(boolean invalidated) {
		this.invalidated = invalidated;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public Repair getRepair() {
		return repair;
	}

	public void setRepair(Repair repair) {
		this.repair = repair;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public List<RequestedPart> getParts() {
		return parts;
	}
	
	public void setParts(List<RequestedPart> parts) {
		this.parts = parts;
	}
}
