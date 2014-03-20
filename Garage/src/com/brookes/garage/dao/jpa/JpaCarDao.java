package com.brookes.garage.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.brookes.garage.dao.CarDao;
import com.brookes.garage.entity.Customers_car;

public class JpaCarDao implements CarDao {

	private EntityManagerFactory emf;

	public JpaCarDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	@Override
	public void addCar(Customers_car car) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(car);
		t.commit();
		em.close();
	}

	@Override
	public void updateCar(Customers_car car) {
		
	}

}
