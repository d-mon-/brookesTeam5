package com.brookes.garage.dao;

import java.util.List;

import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Model;

public interface ModelDao {
	public List<Model> getAllModels();
	public List<Model> getModelsByBrand(Brand brand);
	public void addModel(Model model);
	public void removeModel(Model model);
	public void updateModel(Model model);
}