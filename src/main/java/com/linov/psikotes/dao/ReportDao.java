package com.linov.psikotes.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;
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
	
	@Transactional
	public BigInteger getTheMostTrueAnsQuest(String id) {
		Query query  = super.entityManager
				.createNativeQuery("Select count(*) FROM group1.tbl_detail_applicant_answer WHERE applicant_answer_id = :field1")
				.setParameter("field1", id);
		BigInteger count =  (BigInteger) query.getSingleResult(); 
		return count;
	}
	
}
