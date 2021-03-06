package com.brookes.garage.dao;


import com.brookes.garage.dao.jpa.JpaBrandDao;
import com.brookes.garage.dao.jpa.JpaCarDao;
import com.brookes.garage.dao.jpa.JpaCustomerDao;
import com.brookes.garage.dao.jpa.JpaEstimateDao;
import com.brookes.garage.dao.jpa.JpaInvoiceDao;
import com.brookes.garage.dao.jpa.JpaModelDao;
import com.brookes.garage.dao.jpa.JpaPartDao;
import com.brookes.garage.dao.jpa.JpaRepairDao;
import com.brookes.garage.dao.jpa.JpaRequestedPartDao;
import com.brookes.garage.dao.jpa.JpaStatusDao;
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
	
	public static RequestedPartDao getRequestedPartDao() {
		return new JpaRequestedPartDao(PersistenceManager.getEmf());
	}
	
	public static RepairDao getRepairDao() {
		return new JpaRepairDao(PersistenceManager.getEmf());
	}
	
	public static EstimateDao getEstimateDao() {
		return new JpaEstimateDao(PersistenceManager.getEmf());
	}
	
	public static InvoiceDao getInvoiceDao() {
		return new JpaInvoiceDao(PersistenceManager.getEmf());
	}
	
	public static CarDao getCarDao() {
		return new JpaCarDao(PersistenceManager.getEmf());
	}
	
	public static StatusDao getStatusDao() {
		return new JpaStatusDao(PersistenceManager.getEmf());
	}
}