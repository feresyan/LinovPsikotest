package com.linov.psikotes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.QuestionType;

@Repository("questionTypeDao")
public class QuestionTypeDao extends CommonDao{

	@Transactional
	public QuestionType save (QuestionType questionType) {
		return super.entityManager.merge(questionType);
		
	}
	
	public void delete(String id) {
		QuestionType questionType= findById(id);
		questionType.setActiveState("inactive");
		super.entityManager.merge(questionType);
//		super.entityManager.remove(role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<QuestionType> getAll() {
		List<QuestionType> list = super.entityManager
				.createQuery("from QuestionType where active_state=:status")
				.setParameter("status", "active")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<QuestionType>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public QuestionType findById(String id) {
		List<QuestionType> list = super.entityManager
				.createQuery("from QuestionType where question_type_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new QuestionType();
		else
			return (QuestionType)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public QuestionType findByName(String title) {
		List<QuestionType> list = super.entityManager
				.createQuery("from QuestionType where question_type_title= :field1")
				.setParameter("field1", title)
				.getResultList();
		if(list.size()==0)
			return new QuestionType();
		else
			return (QuestionType)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public QuestionType findByTitle(String title) {
		List<QuestionType> list = super.entityManager
				.createQuery("from QuestionType where question_type_title=:title")
				.setParameter("title", title)
				.getResultList();
		if(list.size()==0)
			return new QuestionType();
		else
			return (QuestionType)list.get(0);
	}
	
}
