package com.brookes.garage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.CarDao;
import com.brookes.garage.entity.Customer;
import com.brookes.garage.entity.Customers_car;

@SuppressWarnings("unchecked")
public class JpaCarDao implements CarDao {

	private EntityManagerFactory emf;

	public JpaCarDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	@Override
	public List<Customers_car> getCarsByCustomer(Customer customer) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT c FROM Customers_car AS c WHERE c.customer.id = " + customer.getId());
		List<Customers_car> cars = query.getResultList();
		em.close();
		return cars;
	}
	@Override
	public void addCar(Customers_car car) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(car);
		t.commit();
		em.close();
	}

	@Override
	public void updateCar(Customers_car car) {
		
	}

}
