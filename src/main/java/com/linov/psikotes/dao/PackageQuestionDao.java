package com.linov.psikotes.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.PackageQuestion;
import com.linov.psikotes.pojo.PojoSearchPackageQuestion;

@Repository("packageQuestionDao")
public class PackageQuestionDao extends CommonDao {

	@Transactional
	public PackageQuestion save (PackageQuestion packageQuestion) {
		return super.entityManager.merge(packageQuestion);
		
	}
	
	public void delete(String id) {
		PackageQuestion packageQuestion= findById(id);
		packageQuestion.setActiveState("inactive");
		super.entityManager.merge(packageQuestion);
//		super.entityManager.remove(role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PackageQuestion> getAll() {
		List<PackageQuestion> list = super.entityManager
				.createQuery("from PackageQuestion where lower(active_state) = :status")
				.setParameter("status", "active")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<PackageQuestion>)list;
	}
		
	@SuppressWarnings("unchecked")
	@Transactional
	public PackageQuestion findById(String id) {
		List<PackageQuestion> list = super.entityManager
				.createQuery("from PackageQuestion where package_question_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new PackageQuestion();
		else
			return (PackageQuestion)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PackageQuestion> findByPackageId(String id) {
		List<PackageQuestion> list = super.entityManager
				.createQuery("from PackageQuestion where package_id = :id and lower(active_state) = :field2")
				.setParameter("id", id)
				.setParameter("field2", "active")
				.getResultList();
		if(list.size()==0)
			return null;
		else
			return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public PackageQuestion findPackageQuestion(String packageId, String questionId) {
		List<PackageQuestion> list = super.entityManager
				.createQuery("from PackageQuestion where package_id=:packageId and question_id=:questionId")
				.setParameter("packageId", packageId)
				.setParameter("questionId", questionId)
				.getResultList();
		if(list.size()==0)
			return new PackageQuestion();
		else
			return (PackageQuestion)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PackageQuestion> search(PojoSearchPackageQuestion pq) {
		StringBuilder query = new StringBuilder();
		query.append("from PackageQuestion where 1=1");
		if( pq.getName() != null) {
			query.append(" and lower(question.questionTitle) like :field1");
		}
		if( pq.getType() != null) {
			query.append(" and lower(question.questionType.questionTypeTitle) like :field1");
		}
		
		Query queryExecuted = super.entityManager.createQuery(query.toString());
		
		if (pq.getName()!= null  ) {
			queryExecuted.setParameter("field1", "%" + pq.getName().toLowerCase() + "%");
		}
		
		if (pq.getType()!= null  ) {
			queryExecuted.setParameter("field1", "%" + pq.getType().toLowerCase() + "%");
		}
		
		List<PackageQuestion> list = queryExecuted.getResultList();
		if(list.size()==0) {
			return new ArrayList<PackageQuestion>();
		}else {
			return list;
		}
	}
	
}
