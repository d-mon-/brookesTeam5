package com.brookes.garage.dao.jpa;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.CustomerDao;
import com.brookes.garage.entity.Customer;


public class JpaCustomerDao implements CustomerDao {

	private EntityManagerFactory emf;

	public JpaCustomerDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	/**
	 * Returns a list of all Customer
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT c FROM Customer AS c WHERE c.deleted=0");
		List<Customer> customers = query.getResultList();
		em.close();
		return customers;
	}
	
	/**
	 * Add a customer to the database
	 */
	@Override
	public void addCustomer(Customer customer) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(customer);
		t.commit();
		em.close();
	}
	
	/**
	 * Remove a customer from the database
	 */
	@Override
	public void removeCustomer(Customer customer) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		customer.setDeleted(true);
		em.merge(customer);
		t.commit();
		em.close();
	}
	
	/**
	 * Update a Customer in the database
	 */
	@Override
	public void updateCustomer(Customer customer) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			em.merge(customer);
		t.commit();
		em.close();
	}
	
}
