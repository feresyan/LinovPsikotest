package com.linov.psikotes.dao;

import java.util.List;

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
