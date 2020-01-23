package com.linov.psikotes.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.Package;
import com.linov.psikotes.pojo.PojoPackage;

@Repository("packageDao")
public class PackageDao extends CommonDao{

	@Transactional
	public Package save (Package pack) {
		return super.entityManager.merge(pack);
		
	}
	
	public void delete(String id) {
		Package pack = findById(id);
		pack.setActiveState("inactive");
		super.entityManager.merge(pack);
//		super.entityManager.remove(role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PojoPackage> getAll() {
		List<Package> list = super.entityManager
				.createQuery("from Package where active_state=:status")
				.setParameter("status", "active")
				.getResultList();
		
		if(list.size()==0) 
		{
			return null;
		} else {
			List<PojoPackage> listPojoPackage = new ArrayList<PojoPackage>();
			
			for (int i = 0; i < list.size(); i++) {
				
				PojoPackage pjPack = new PojoPackage();
				
				Query query  = super.entityManager
						.createNativeQuery("Select count(*) FROM tbl_package_question WHERE package_id = :field1")
						.setParameter("field1", list.get(i));
				BigInteger count =  (BigInteger) query.getSingleResult();
				
				pjPack.setPackageId(list.get(i).getPackageId());
				pjPack.setPackageName(list.get(i).getPackageName());
				pjPack.setAmountOfQuestion(count.intValue());
				pjPack.setAmountOfTime(list.get(0).getTime());
				listPojoPackage.add(pjPack);
			}
			
			return listPojoPackage;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Package findById(String id) {
		List<Package> list = super.entityManager
				.createQuery("from Package where package_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new Package();
		else
			return (Package)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Package findByTitle(String title) {
		List<Package> list = super.entityManager
				.createQuery("from Package where package_name=:title")
				.setParameter("title", title)
				.getResultList();
		if(list.size()==0)
			return new Package();
		else
			return (Package)list.get(0);
	}
	
}
