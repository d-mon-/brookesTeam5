package com.brookes.garage.dao;

import java.util.List;

import com.brookes.garage.entity.Estimate;
import com.brookes.garage.entity.Model;
import com.brookes.garage.entity.Part;

public interface PartDao {	
	public List<Part> getAllParts();
	public List<Part> getPartsByModel(Model model);
	public List<Part> getPartsByEstimate(Estimate estimate);
	public void addPart(Part part);
	public void removePart(Part part);
	public void updatePart(Part part);
}

