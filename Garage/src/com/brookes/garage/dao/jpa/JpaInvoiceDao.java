package com.brookes.garage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.InvoiceDao;
import com.brookes.garage.entity.Invoice;

public class JpaInvoiceDao implements InvoiceDao {

	private EntityManagerFactory emf;

	public JpaInvoiceDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Invoice> getAllInvoices() {		
		List<Invoice> invoices = null;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("SELECT b FROM Invoice AS b");
			invoices = query.getResultList();
		} finally {
			em.close();			
		}
		return invoices;
	}

	@Override
	public void addInvoice(Invoice invoice) {		
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.persist(invoice);
			t.commit();
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
	}

	@Override
	public void updateInvoice(Invoice invoice) {
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
				em.merge(invoice);
			t.commit();
		} finally {
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			em.close();			
		}
	}

}
