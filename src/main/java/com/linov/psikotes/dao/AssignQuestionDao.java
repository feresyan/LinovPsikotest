package com.linov.psikotes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.AssignQuestion;

@Repository("assignQuestionDao")
public class AssignQuestionDao extends CommonDao{

	@Transactional
	public AssignQuestion save (AssignQuestion aq) {
		return super.entityManager.merge(aq);
		
	}
	
	public void delete(String id) {
		AssignQuestion aq= findById(id);
		aq.setActiveState("inactive");
		super.entityManager.merge(aq);
//		super.entityManager.remove(role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AssignQuestion> getAll() {
		List<AssignQuestion> list = super.entityManager
				.createQuery("from AssignQuestion where active_state=:status")
				.setParameter("status", "active")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<AssignQuestion>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public AssignQuestion findById(String id) {
		List<AssignQuestion> list = super.entityManager
				.createQuery("from AssignQuestion where assign_question_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new AssignQuestion();
		else
			return (AssignQuestion)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public AssignQuestion findBK(String userId, String pqId) {
		List<AssignQuestion> list = super.entityManager
				.createQuery("from AssignQuestion where user_id=:userId and package_question_id=:pqId")
				.setParameter("userId", userId)
				.setParameter("pqId", pqId)
				.getResultList();
		if(list.size()==0)
			return new AssignQuestion();
		else
			return (AssignQuestion)list.get(0);
	}
}
