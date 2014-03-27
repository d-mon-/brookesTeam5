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

	public JpaModelDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
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
	
	@Override
	public void addModel(Model model) {
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
	
	@Override
	public void removeModel(Model model){			
		EntityManager em = emf.createEntityManager();
		try {
			Part myPart = null;
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
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
	}

}
