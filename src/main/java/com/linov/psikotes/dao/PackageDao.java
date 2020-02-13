package com.linov.psikotes.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.Package;
import com.linov.psikotes.pojo.PojoPackage;
import com.linov.psikotes.pojo.PojoSearchPackage;

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
	
	@Transactional
	public BigInteger getTotalQuestion(String id) {
		Query query  = super.entityManager
				.createNativeQuery("Select count(*) FROM group1.tbl_package_question WHERE package_id = :field1 and lower(active_state) = :field2")
				.setParameter("field1", id)
				.setParameter("field2", "active");
		BigInteger count =  (BigInteger) query.getSingleResult(); 
		return count;
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
								
				pjPack.setPackageId(list.get(i).getPackageId());
				pjPack.setPackageName(list.get(i).getPackageName());
				pjPack.setAmountOfTime(list.get(i).getAmountOfTime());
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
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Package> search(PojoSearchPackage pack) {
		StringBuilder query = new StringBuilder();
		query.append("from Package where 1=1");
		if( pack.getPackageName() != null) {
			query.append(" and lower(package_name) like :field1");
		}
		
		Query queryExecuted = super.entityManager.createQuery(query.toString());
		
		if (pack.getPackageName()!= null  ) {
			queryExecuted.setParameter("field1", "%" + pack.getPackageName().toLowerCase() + "%");
		}
		
		List<Package> list = queryExecuted.getResultList();
		if(list.size()==0) {
			return new ArrayList<Package>();
		}else {
			return list;
		}
	}
}
