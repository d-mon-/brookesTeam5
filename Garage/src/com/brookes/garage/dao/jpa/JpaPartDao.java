package com.brookes.garage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.PartDao;
import com.brookes.garage.entity.Estimate;
import com.brookes.garage.entity.Model;
import com.brookes.garage.entity.Part;

@SuppressWarnings("unchecked")
public class JpaPartDao implements PartDao {

	private EntityManagerFactory emf;

	
	/**
	 * The constructor method
	 */
	public JpaPartDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	/**
	 * Return a list of all Part not marked as deleted
	 */
	@Override
	public List<Part> getAllParts() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT p FROM Part AS p WHERE p.delete_flag=0");
		List<Part> parts = query.getResultList();
		em.close();
		return parts;
	}

	/**
	 * Return a list of all Part not marked as deleted for a given Model
	 */
	@Override
	public List<Part> getPartsByModel(Model model) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT p FROM Part AS p WHERE p.delete_flag=0 AND p.model.id = " + model.getId());
		List<Part> parts = query.getResultList();
		em.close();
		return parts;
	}
	
	/**
	 * Return a list of all Part not marked as deleted for a given Estimate
	 */
	@Override
	public List<Part> getPartsByEstimate(Estimate estimate) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT p FROM Part AS p WHERE p.delete_flag=0 AND p.estimate.id = " + estimate.getId());
		List<Part> parts = query.getResultList();
		em.close();
		return parts;
	}
	
	/**
	 * Add a Part to the database
	 */
	@Override
	public void addPart(Part part) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(part);
		t.commit();
		em.close();
	}

	/**
	 * Update a Part in the database
	 */
	@Override
	public void updatePart(Part part) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			em.merge(part);
		t.commit();
		em.close();
	}
	
	/**
	 * Remove a Part from the database
	 */
	@Override
	public void removePart(Part part){
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			Part partToUpdate = em.find(Part.class, part.getId());
			partToUpdate.setDelete_flag(true);		
			em.persist(partToUpdate);
		t.commit();
		em.close();	
	}
}
