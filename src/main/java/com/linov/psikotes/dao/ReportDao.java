package com.linov.psikotes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.Role;

@Repository("reportDao")
public class ReportDao extends CommonDao{
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Role> getAll() {
		List<Role> list = super.entityManager
				.createQuery("from Role where active_state=:status")
				.setParameter("status", "active")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<Role>)list;
	}
	
}
