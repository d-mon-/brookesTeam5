package com.brookes.garage.dao;


import com.brookes.garage.dao.jpa.JpaModelDao;
import com.brookes.garage.dao.jpa.JpaBrandDao;
import com.brookes.garage.dao.jpa.JpaCustomerDao;
import com.brookes.garage.dao.jpa.JpaPartDao;
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
	
	public static ModelDao getModelDao() {
		return new JpaModelDao(PersistenceManager.getEmf());
	}
	
	public static PartDao getPartDao() {
		return new JpaPartDao(PersistenceManager.getEmf());
	}
}