package com.brookes.garage.dao;

import com.brookes.garage.dao.jpa.JpaBrandDao;
import com.brookes.garage.dao.jpa.JpaCustomerDao;
import com.brookes.garage.util.PersistenceManager;

public class DaoFactory {

	private DaoFactory() {
		super();
	}

	public static CustomerDao getCustomerDao() {
		return new JpaCustomerDao(PersistenceManager.getEmf());
	}
	
	public static BrandDao getBrandDao() {
		return new JpaBrandDao(PersistenceManager.getEmf());
	}
}