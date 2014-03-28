package com.brookes.garage.dao;

import java.util.List;

import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.CustomersCar;

public interface CarDao {

	public List<CustomersCar> getCarsByCustomer(Customer customer);
	public void addCar(CustomersCar car);
	
}
