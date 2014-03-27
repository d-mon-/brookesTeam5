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
		List<Estimate> estimates = null;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("SELECT b FROM Estimate AS b");
			estimates = query.getResultList();
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
		return estimates;
	}

	@Override
	public void addEstimate(Estimate estimate) {
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.persist(estimate);
			t.commit();
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
	}

	@Override
	public void updateEstimate(Estimate estimate) {
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
				em.merge(estimate);
			t.commit();
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
	}

}
