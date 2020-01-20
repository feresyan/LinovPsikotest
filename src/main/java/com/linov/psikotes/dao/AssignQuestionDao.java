package com.linov.psikotes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.AssignQuestion;
import com.linov.psikotes.entity.PackageQuestion;

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
	public List<PackageQuestion> getAllQuestByUserId(String userId) {
		List<AssignQuestion> list = super.entityManager
				.createQuery("from AssignQuestion where user_id=:field1")
				.setParameter("field1", userId)
				.getResultList();
		
		List<PackageQuestion> listQuestion = super.entityManager
				.createQuery("from PackageQuestion where package_id=:field1")
				.setParameter("field1", list.get(0).getPack().getPackageId())
				.getResultList();
		if(list.size()==0)
			return null;
		else
			return listQuestion;
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
	public AssignQuestion findBK(String userId, String packId) {
		List<AssignQuestion> list = super.entityManager
				.createQuery("from AssignQuestion where user_id = :field1 and package_id = :field2")
				.setParameter("field1", userId)
				.setParameter("field2", packId)
				.getResultList();
		if(list.size()==0)
			return new AssignQuestion();
		else
			return (AssignQuestion)list.get(0);
	}
}
