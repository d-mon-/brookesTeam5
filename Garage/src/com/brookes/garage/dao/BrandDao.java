package com.brookes.garage.dao;

import java.util.List;

import com.brookes.garage.entity.Brand;

public interface BrandDao {

	public List<Brand> getAllBrands();
	public void addBrand(Brand brand);
	public void removeBrand(Brand brand);
	public void updateBrand(Brand brand);
	
}
