package com.brookes.garage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.PartDao;
import com.brookes.garage.entity.Model;
import com.brookes.garage.entity.Part;

public class JpaPartDao implements PartDao {

	private EntityManagerFactory emf;

	public JpaPartDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Part> getAllParts() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT b FROM Part AS b");
		List<Part> parts = query.getResultList();
		em.close();
		return parts;
	}

	@Override
	public void addPart(Part part) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(part);
		t.commit();
		em.close();
	}

	@Override
	public void removePart(Part part) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		Part b = em.getReference(Part.class, part.getId());
		em.remove(b);
		t.commit();
		em.close();
	}

	@Override
	public void updatePart(Part part) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			em.merge(part);
		t.commit();
		em.close();
	}
	
	@Override
	public void invalidateEntry(Part part){
		Part myPart = null;
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
