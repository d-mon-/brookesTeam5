package com.brookes.garage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.CarDao;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.CustomersCar;

@SuppressWarnings("unchecked")
public class JpaCarDao implements CarDao {

	private EntityManagerFactory emf;

	/**
	 * The constructor Method
	 */
	public JpaCarDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	/**
	 * Return the list of cars for a given customer
	 */
	@Override
	public List<CustomersCar> getCarsByCustomer(Customer customer) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT c FROM Customers_car AS c WHERE c.customer.id = " + customer.getId());
		List<CustomersCar> cars = query.getResultList();
		em.close();
		return cars;
	}
	
	/**
	 * Add a Car to the database
	 */
	@Override
	public void addCar(CustomersCar car) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(car);
		t.commit();
		em.close();
	}

}
