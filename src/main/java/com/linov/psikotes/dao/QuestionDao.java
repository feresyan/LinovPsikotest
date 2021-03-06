package com.linov.psikotes.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.PackageQuestion;
import com.linov.psikotes.entity.Question;
import com.linov.psikotes.pojo.PojoSearchQuestion;

@Repository("questionDao")
public class QuestionDao extends CommonDao {

	@Transactional
	public Question save (Question question) {
		return super.entityManager.merge(question);
		
	}
	
	public void delete(String id) {
		Question question= findById(id);
		question.setActiveState("inactive");
		super.entityManager.merge(question);
//		super.entityManager.remove(role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Question> getAll() {
		List<Question> list = super.entityManager
				.createQuery("from Question where active_state=:status order by timestamp desc")
				.setParameter("status", "active")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<Question>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PackageQuestion> getAllQuestByPackage(String packageName) {
		List<PackageQuestion> list = super.entityManager
				.createQuery("from PackageQuestion jp where lower(jp.pack.packageName) =:field1")
				.setParameter("field1", packageName)
				.getResultList();
		if(list.size()==0) return list;
		else return (List<PackageQuestion>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Question findById(String id) {
		List<Question> list = super.entityManager
				.createQuery("from Question where question_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new Question();
		else
			return (Question)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Question findByTitle(String title) {
		List<Question> list = super.entityManager
				.createQuery("from Question where question_title=:title")
				.setParameter("title", title)
				.getResultList();
		if(list.size()==0)
			return new Question();
		else
			return (Question)list.get(0);
	}
	
//	@SuppressWarnings("unchecked")
//	@Transactional
//	public List<Question> getAllQuestionForPsikotest() {
//		List<Question> list = super.entityManager
//				.createQuery("from Question")
//				.getResultList();
//		if(list.size()==0)
//			return null;
//		else
//			return list;
//	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Question> search(PojoSearchQuestion searchQuest) {
		StringBuilder query = new StringBuilder();
		query.append("from Question where 1=1");
		if( searchQuest.getType() != null) {
			query.append(" and lower(questionType.questionTypeTitle) like :field1");
		}
		if(searchQuest.getTitle() != null ) {
			query.append(" and lower(question_title) like :field2 ");
		}
//		if(searchQuest.getDescription() != null ) {
//			query.append(" and lower(question_desc) like :field3 ");
//		}
		
		Query queryExecuted = super.entityManager.createQuery(query.toString());
		
		if (searchQuest.getType() != null  ) {
			queryExecuted.setParameter("field1", "%" + searchQuest.getType().toLowerCase() + "%");
		}
		if (searchQuest.getTitle() != null ) {
			queryExecuted.setParameter("field2", "%" + searchQuest.getTitle().toLowerCase() + "%");
		}
//		if (searchQuest.getDescription() != null ) {
//			queryExecuted.setParameter("field3", "%" + searchQuest.getDescription().toLowerCase() + "%");
//		}
		
		List<Question> list = queryExecuted.getResultList();
		if(list.size()==0) {
			return new ArrayList<Question>();
		}else {
			return list;
		}
	}
	
}
