package com.brookes.garage.dao;

import java.util.List;

import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.Customers_car;
import com.brookes.garage.entity.Model;

public interface CarDao {

	public List<Customers_car> getCarsByCustomer(Customer customer);
	public void addCar(Customers_car car);
	public void updateCar(Customers_car car);
	
}
