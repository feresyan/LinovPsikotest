package com.linov.psikotes.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.HeaderApplicantAnswer;
import com.linov.psikotes.pojo.PojoSearchHeaderAppAns;

@Repository("headerAppAnsDao")
public class HeaderAppAnsDao extends CommonDao {

	@Transactional
	public HeaderApplicantAnswer save (HeaderApplicantAnswer headAppAns) {
		return super.entityManager.merge(headAppAns);
		
	}
	
	public void delete(String id) {
		HeaderApplicantAnswer headAppAns= findById(id);
		headAppAns.setActiveState("inactive");
		super.entityManager.merge(headAppAns);
//		super.entityManager.remove(role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<HeaderApplicantAnswer> getAll() {
		List<HeaderApplicantAnswer> list = super.entityManager
				.createQuery("from HeaderApplicantAnswer where active_state=:status")
				.setParameter("status", "active")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<HeaderApplicantAnswer>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public HeaderApplicantAnswer findById(String id) {
		List<HeaderApplicantAnswer> list = super.entityManager
				.createQuery("from HeaderApplicantAnswer where applicant_answer_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new HeaderApplicantAnswer();
		else
			return (HeaderApplicantAnswer)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public HeaderApplicantAnswer findByUser(String id) {
		List<HeaderApplicantAnswer> list = super.entityManager
				.createQuery("from HeaderApplicantAnswer where user_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new HeaderApplicantAnswer();
		else
			return (HeaderApplicantAnswer)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<HeaderApplicantAnswer> search(PojoSearchHeaderAppAns head) {
		StringBuilder query = new StringBuilder();
		query.append("from HeaderApplicantAnswer where 1=1");
		if( head.getName() != null) {
			query.append(" and lower(user.profile.profileName) like :field1");
		}
		
		Query queryExecuted = super.entityManager.createQuery(query.toString());
		
		if (head.getName()!= null  ) {
			queryExecuted.setParameter("field1", "%" + head.getName().toLowerCase() + "%");
		}
		
		List<HeaderApplicantAnswer> list = queryExecuted.getResultList();
		if(list.size()==0) {
			return new ArrayList<HeaderApplicantAnswer>();
		}else {
			return list;
		}
	}
	
}
