package com.brookes.garage.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.brookes.garage.dao.BrandDao;
import com.brookes.garage.entity.Brand;
import com.brookes.garage.entity.Model;
import com.brookes.garage.entity.Part;

public class JpaBrandDao implements BrandDao {

	private EntityManagerFactory emf;

	public JpaBrandDao(EntityManagerFactory emf) {
		super();
		this.emf=emf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Brand> getAllBrands() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT b FROM Brand AS b");
		List<Brand> brands = query.getResultList();
		em.close();
		return brands;
	}

	@Override
	public void addBrand(Brand brand) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(brand);
		t.commit();
		em.close();
	}

	@Override
	public void removeBrand(Brand brand) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		Brand b = em.getReference(Brand.class, brand.getId());
		em.remove(b);
		t.commit();
		em.close();
	}

	@Override
	public void updateBrand(Brand brand) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			Brand brandToUpdate = em.find(Brand.class, brand.getId());
			brandToUpdate.setName(brand.getName());
			em.persist(brandToUpdate);
		t.commit();
		em.close();
	}
	
	@Override
	public void invalidateEntry(Brand brand){
		Model myModel = null;
		Part myPart = null;
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
			Brand brandToUpdate = em.find(Brand.class, brand.getId());
			brandToUpdate.setDelete_flag(true);			
			List<Model> models = brandToUpdate.getModels();
			for(int i = 0, _l = models.size(); i<_l;i++){
				myModel = models.get(i);
				myModel.setDelete_flag(true);
				List<Part> parts = myModel.getParts();
				for(int j = 0, __l = parts.size(); j<__l;j++){
					myPart = parts.get(j);
					myPart.setDelete_flag(true);
					em.persist(myPart);
				}
				em.persist(myModel);
			}
			em.persist(brandToUpdate);
		t.commit();
		em.close();	
	}

}
