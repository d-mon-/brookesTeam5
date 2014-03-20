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
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT b FROM Repair AS b");
		List<Repair> repairs = query.getResultList();
		em.close();
		return repairs;
	}

	@Override
	public void addRepair(Repair repair) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(repair);
		t.commit();
		em.close();
	}

	@Override
	public void updateRepair(Repair repair) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			Repair repairToUpdate = em.find(Repair.class, repair.getId());
			repairToUpdate.setCar(repair.getCar());
			repairToUpdate.setCreation_date(repair.getCreation_date());
			repairToUpdate.setDescription(repair.getDescription());
			repairToUpdate.setIdentifier(repair.getIdentifier());
			repairToUpdate.setStatus(repair.getStatus());
			em.persist(repair);
		t.commit();
		em.close();
	}

}
