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

	public JpaEstimateDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Estimate> getAllEstimates() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT b FROM Estimate AS b");
		List<Estimate> estimates = query.getResultList();
		em.close();
		return estimates;
	}

	@Override
	public void addEstimate(Estimate estimate) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(estimate);
		t.commit();
		em.close();
	}

	@Override
	public void updateEstimate(Estimate estimate) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			Estimate estimateToUpdate = em.find(Estimate.class, estimate.getId());
			estimateToUpdate.setCreation_date(estimate.getCreation_date());
			estimateToUpdate.setIdentifier(estimate.getIdentifier());
			estimateToUpdate.setInvalidated(estimate.isInvalidated());
			estimateToUpdate.setInvoice(estimate.getInvoice());
			estimateToUpdate.setRepair(estimate.getRepair());
			em.persist(estimate);
		t.commit();
		em.close();
	}

}