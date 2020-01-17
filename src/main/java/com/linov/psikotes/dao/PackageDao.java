package com.linov.psikotes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.Package;

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
	public List<Package> getAll() {
		List<Package> list = super.entityManager
				.createQuery("from Package where active_state=:status")
				.setParameter("status", "active")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<Package>)list;
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
