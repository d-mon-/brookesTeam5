package com.brookes.garage.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.brookes.garage.dao.DaoFactory;

public class PersistenceManager {

private static EntityManagerFactory emf;
	
	public static EntityManagerFactory getEmf() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("myPersistence");
			
			DaoFactory.getStatusDao().initialiseStatus();
		}
		return emf;
	}

	public static void setEmf(EntityManagerFactory emf) {
		PersistenceManager.emf = emf;
	}

	public static void closeFactoryInstance() {
		 if(emf != null && emf.isOpen()) emf.close();
	}
	
}
