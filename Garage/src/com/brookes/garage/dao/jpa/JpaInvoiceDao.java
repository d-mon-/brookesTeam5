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

	/**
	 * The constructor Method
	 */
	public JpaInvoiceDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	/**
	 * Return a list of all Invoice
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Invoice> getAllInvoices() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT b FROM Invoice AS b");
		List<Invoice> invoices = query.getResultList();
		em.close();
		return invoices;
	}

	/**
	 * Add an Invoice to the database
	 */
	@Override
	public void addInvoice(Invoice invoice) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(invoice);
		t.commit();
		em.close();
	}

	/**
	 * Update an Invoice in the database
	 */
	@Override
	public void updateInvoice(Invoice invoice) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			em.merge(invoice);
		t.commit();
		em.close();
	}

}
