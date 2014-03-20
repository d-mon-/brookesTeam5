package com.brookes.garage.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

private static EntityManagerFactory emf;
	
	public static EntityManagerFactory getEmf() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("myPersistence");
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
