package com.brookes.garage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.RepairDao;
import com.brookes.garage.entity.Repair;

public class JpaRepairDao implements RepairDao {

	private EntityManagerFactory emf;

	public JpaRepairDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Repair> getAllRepairs() {
		List<Repair> repairs = null;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("SELECT r FROM Repair AS r");
			 repairs = query.getResultList();
		} finally {
			em.close();			
		}
		return repairs;
	}

	@Override
	public void addRepair(Repair repair) {		
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.persist(repair);
			t.commit();
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
	}

	@Override
	public void updateRepair(Repair repair) {		
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
				em.merge(repair);
			t.commit();
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
	}

}
