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
		Query query = em.createQuery("SELECT b FROM Model AS b");
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
			Model modelToUpdate = em.find(Model.class, model.getId());
			modelToUpdate.setName(model.getName());
			em.persist(modelToUpdate);
		t.commit();
		em.close();
	}
	
	@Override
	public void invalidateEntry(Model model){
		Part myPart = null;
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			Model modelToUpdate = em.find(Model.class, model.getId());
			modelToUpdate.setDelete_flag(true);			
			List<Part> parts = modelToUpdate.getParts();
			for(int j = 0, __l = parts.size(); j<__l;j++){
				myPart = parts.get(j);
				myPart.setDelete_flag(true);
				em.persist(myPart);
			}
			em.persist(modelToUpdate);
		t.commit();
		em.close();	
	}

}
