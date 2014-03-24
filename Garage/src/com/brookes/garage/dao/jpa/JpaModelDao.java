package com.brookes.garage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.ModelDao;
import com.brookes.garage.entity.Model;

public class JpaModelDao implements ModelDao {

	private EntityManagerFactory emf;

	public JpaModelDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Model> getAllModels() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT m FROM Model AS m");
		List<Model> models = query.getResultList();
		em.close();
		return models;
	}

	@Override
	public void addModel(Model model) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(model);
		/*
		Brand brand = model.getBrand();
		List<Model> models = brand.getModels();
		models.add(model);
		brand.setModels(models);
		em.merge(brand);
		*/
		t.commit();
		em.close();
	}

	@Override
	public void removeModel(Model model) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		Model b = em.getReference(Model.class, model.getId());
		em.remove(b);
		t.commit();
		em.close();
	}

	@Override
	public void updateModel(Model model) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			em.merge(model);
		t.commit();
		em.close();
	}

}
