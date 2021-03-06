package com.brookes.garage.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.StatusDao;
import com.brookes.garage.entity.Repair;
import com.brookes.garage.entity.Status;

public class JpaStatusDao implements StatusDao {

	private EntityManagerFactory emf;

	/**
	 * The constructor method
	 */
	public JpaStatusDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	/**
	 * Return the first status
	 */
	@Override
	public Status getFirstStatus() {
		Status status = null;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("SELECT s FROM Status AS s WHERE s.reference = \"S1\"");
			status = (Status) query.getSingleResult();
		} finally {
			em.close();			
		}
		return status;
	}

	/**
	 * Return the status requiring to create an estimate
	 */
	@Override
	public Status getEstimateStatus() {
		Status status = null;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("SELECT s FROM Status AS s WHERE s.reference = \"S2\"");
			status = (Status) query.getSingleResult();
		} finally {
			em.close();			
		}
		return status;
	}
	
	/**
	 * Return the status requiring to create an invoice
	 */
	@Override
	public Status getInvoiceStatus() {		
		Status status = null;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("SELECT s FROM Status AS s WHERE s.reference = \"S3\"");
			status = (Status) query.getSingleResult();
		} finally {
			em.close();			
		}
		return status;
	}
	
	/**
	 * Initialise the status in the database if there is no status
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void initialiseStatus() {
		EntityManager em = emf.createEntityManager();	
		
		try {
			Query query = em.createQuery("SELECT s FROM Status AS s");
			List<Status> status = query.getResultList();
			
			if (status.size() == 0) {
				Status one = new Status();
				one.setReference("S1");
				one.setTitle("Being diagnosed");
				
				Status two = new Status();
				two.setReference("S2");
				two.setTitle("Diagnostic done");
				
				Status three = new Status();
				three.setReference("S3");
				three.setTitle("Being repaired");
				
				Status four = new Status();
				four.setReference("S4");
				four.setTitle("Finished");
				
				Status five = new Status();
				five.setReference("S5");
				five.setTitle("Refused by client");
				
				Status six = new Status();
				six.setReference("S6");
				six.setTitle("Nothing to report");
				
				List<Status> successors = new ArrayList<Status>();
				successors.add(two);
				successors.add(six);
				one.setSuccessors(successors);
				
				successors = new ArrayList<Status>();
				successors.add(three);
				successors.add(five);
				two.setSuccessors(successors);

				successors = new ArrayList<Status>();
				successors.add(four);
				three.setSuccessors(successors);
				EntityTransaction t = em.getTransaction();
				t.begin();
				em.persist(one);
				em.persist(two);
				em.persist(three);
				em.persist(four);
				em.persist(five);
				em.persist(six);
				t.commit();
			}
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
		
	}

}
