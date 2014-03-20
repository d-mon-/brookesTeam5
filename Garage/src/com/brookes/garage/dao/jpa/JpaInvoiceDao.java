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
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT b FROM Invoice AS b");
		List<Invoice> invoices = query.getResultList();
		em.close();
		return invoices;
	}

	@Override
	public void addInvoice(Invoice invoice) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(invoice);
		t.commit();
		em.close();
	}

	@Override
	public void updateInvoice(Invoice invoice) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			Invoice invoiceToUpdate = em.find(Invoice.class, invoice.getId());
			invoiceToUpdate.setCreation_date(invoice.getCreation_date());
			invoiceToUpdate.setEstimate(invoice.getEstimate());
			invoiceToUpdate.setIdentifier(invoice.getIdentifier());
			em.persist(invoice);
		t.commit();
		em.close();
	}

}
