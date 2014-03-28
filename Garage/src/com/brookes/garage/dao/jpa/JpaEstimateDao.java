package com.brookes.garage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.EstimateDao;
import com.brookes.garage.entity.Estimate;

public class JpaEstimateDao implements EstimateDao {

	private EntityManagerFactory emf;

	/**
	 * The constructor Method
	 */
	public JpaEstimateDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	/**
	 * Return the list of all estimates
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Estimate> getAllEstimates() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT e FROM Estimate AS e");
		List<Estimate> estimates = query.getResultList();
		em.close();
		return estimates;
	}

	/**
	 * Add an Estimate to the database
	 */
	@Override
	public void addEstimate(Estimate estimate) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(estimate);
	
		t.commit();
		em.close();
	}
	
	/**
	 * Update an Estimate in the database
	 */
	@Override
	public void updateEstimate(Estimate estimate) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			em.merge(estimate);
		t.commit();
		em.close();
	}

}
