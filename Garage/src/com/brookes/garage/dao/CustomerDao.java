package com.brookes.garage.dao;
import java.util.List;

import com.brookes.garage.entity.Customer;


public interface CustomerDao {

	public List<Customer> getAllCustomers();
	public void addCustomer(Customer customer);
	public void removeCustomer(Customer customer);
	public void updateCustomer(Customer customer);

}
