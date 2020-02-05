package com.linov.psikotes.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.DetailApplicantAnswer;

@Repository("detailAppAnsDao")
public class DetailAppAnsDao extends CommonDao{
	
	@Transactional
	public DetailApplicantAnswer save (DetailApplicantAnswer dAppAns) {
		return super.entityManager.merge(dAppAns);
		
	}
	
	public void delete(String id) {
		DetailApplicantAnswer dAppAns= findById(id);
//		dAppAns.setActiveState("inactive");
		super.entityManager.merge(dAppAns);
//		super.entityManager.remove(role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<DetailApplicantAnswer> getAll() {
		List<DetailApplicantAnswer> list = super.entityManager
				.createQuery("from DetailApplicantAnswer")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<DetailApplicantAnswer>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<DetailApplicantAnswer> getAllByAppAnsId(String id) {
		List<DetailApplicantAnswer> list = super.entityManager
				.createQuery("from DetailApplicantAnswer where applicant_answer_id = :field1")
				.setParameter("field1", id)
				.getResultList();
		if(list.size()==0) return null;
		else return (List<DetailApplicantAnswer>)list;
	}
	
	@Transactional
	public BigInteger getTotalQuestByAppAnsId(String id) {
		Query query  = super.entityManager
				.createNativeQuery("Select count(*) FROM group1.tbl_detail_applicant_answer WHERE applicant_answer_id = '" + id +"'");
//				.createNativeQuery("Select count(*) FROM tbl_detail_applicant_answer WHERE applicant_answer_id = :field1")
//				.setParameter("field1", id);
		BigInteger count =  (BigInteger) query.getSingleResult(); 
		return count;
	}
	
	@Transactional
	public BigInteger getTotalPointsByAppAnsId(String id) {
		Query query  = super.entityManager
				.createNativeQuery("Select sum(point) FROM group1.tbl_detail_applicant_answer WHERE applicant_answer_id = '" + id +"'");
//				.createNativeQuery("Select sum(point) FROM tbl_detail_applicant_answer WHERE applicant_answer_id = :field1")
//				.setParameter("field1", id);
		BigInteger count =  (BigInteger) query.getSingleResult(); 
		return count;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<DetailApplicantAnswer> getAllEssayQuestByAppAnsId(String id) {
		List<DetailApplicantAnswer> list = super.entityManager
				.createQuery("from DetailApplicantAnswer where applicant_answer_id = :field1 and packQuestion.question.questionType.answerType = :field2")
				.setParameter("field1", id)
				.setParameter("field2", "Essay")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<DetailApplicantAnswer>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public DetailApplicantAnswer findById(String id) {
		List<DetailApplicantAnswer> list = super.entityManager
				.createQuery("from DetailApplicantAnswer where detail_answer_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new DetailApplicantAnswer();
		else
			return (DetailApplicantAnswer)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public DetailApplicantAnswer findBK(String idHeader, String idPackQuest) {
		List<DetailApplicantAnswer> list = super.entityManager
				.createQuery("from DetailApplicantAnswer where applicant_answer_id=:idHeader and package_question_id=:idPackQuest")
				.setParameter("idHeader", idHeader)
				.setParameter("idPackQuest", idPackQuest)
				.getResultList();
		if(list.size()==0)
			return new DetailApplicantAnswer();
		else
			return (DetailApplicantAnswer)list.get(0);
	}

}
