package com.brookes.garage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.ModelDao;
import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Model;
import com.brookes.garage.entity.Part;

@SuppressWarnings("unchecked")
public class JpaModelDao implements ModelDao {

	private EntityManagerFactory emf;

	/**
	 * The constructor method
	 */
	public JpaModelDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	/**
	 * Returns a list of all Model not marked as deleted
	 */
	@Override
	public List<Model> getAllModels() {
		List<Model> models = null;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("SELECT m FROM Model AS m  WHERE b.delete_flag=0");
			models = query.getResultList();
		} finally {
			em.close();			
		}
		return models;
	}

	/**
	 * Returns a list of all Model not marked as deleted for a given Brand
	 */
	@Override
	public List<Model> getModelsByBrand(Brand brand) {
		List<Model> models =  null;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("SELECT m FROM Model AS m WHERE m.delete_flag=0 AND m.brand.id = " + brand.getId());
			models = query.getResultList();
		} finally {
			em.close();			
		}
		return models;
	}
	
	/**
	 * Add a Model to the database
	 */
	@Override
	public void addModel(Model model) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(model);
		t.commit();
		em.close();
	}

	/**
	 * Update a Model in the database
	 */
	@Override
	public void updateModel(Model model) {
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.persist(model);
			t.commit();
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
	}
	
	/**
	 * Remove a Model from the database
	 */
	@Override
	public void removeModel(Model model){
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			Model modelToUpdate = em.find(Model.class, model.getId());
			modelToUpdate.setDelete_flag(true);			
			List<Part> parts = modelToUpdate.getParts();
			
			for (Part part : parts) {
				part.setDelete_flag(true);
				em.persist(part);
			}
			
			em.persist(modelToUpdate);
		t.commit();
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
	}

}
