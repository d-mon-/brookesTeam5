package com.brookes.garage.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.brookes.garage.dao.RequestedPartDao;
import com.brookes.garage.entity.RequestedPart;

public class JpaRequestedPartDao implements RequestedPartDao {

	private EntityManagerFactory emf;

	public JpaRequestedPartDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	@Override
	public void addRequestedPart(RequestedPart requestedPart) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(requestedPart);
		t.commit();
		em.close();
	}
}
