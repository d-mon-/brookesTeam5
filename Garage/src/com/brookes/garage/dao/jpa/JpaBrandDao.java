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
		Query query = em.createQuery("SELECT b FROM Brand AS b  WHERE b.delete_flag=0");
		List<Brand> brands = query.getResultList();	
		brands = reduce(brands);
		em.close();
		return brands;
	}
	//remove models and parts deleted (delete_flag==true)
	private List<Brand> reduce(List<Brand> brands){
		if(brands.size()>0){
			Brand br = null;
			Model mod = null;
			for(int i = 0, l = brands.size();i<l;i++){
				br = brands.get(i);			
				for(int j = 0, ll = br.getModels().size();j<ll;j++){
					mod = br.getModels().get(j);
					if(mod.isDelete_flag()){
						br.getModels().remove(j);
					}else{
						for(int k = 0, lll = mod.getParts().size();k<lll;k++){
							if(mod.getParts().get(k).isDelete_flag()){
								mod.getParts().remove(k);
							}
						}
					}
				}
			}
		}
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
			em.merge(brand);
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
