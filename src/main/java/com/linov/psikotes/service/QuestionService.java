package com.linov.psikotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.QuestionDao;
import com.linov.psikotes.dao.QuestionTypeDao;
import com.linov.psikotes.entity.Question;
import com.linov.psikotes.entity.QuestionType;
import com.linov.psikotes.entity.SearchQuestion;

@Service("questionService")
public class QuestionService {

	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuestionTypeDao questionTypeDao;
	
	public List<Question> getAllQuestion(){
		List<Question> list = questionDao.getAll();
		return list;
	}
	
	public List<Question> search(SearchQuestion searchQuest){
		List<Question> list = questionDao.search(searchQuest);
		return list;
	}
	
	public Question findById(String id) {
		Question question = questionDao.findById(id);
		return question;
	}
	
	public Question insertQuestion(Question question) throws Exception{
		try {
			//Check if id null
			valIdNull(question);
			
			//Check if question title not null
			valBkNotNull(question);
			
			//Check if question title not exist in DB
			valBkNotExist(questionDao.findByTitle(question.getQuestionTitle()));
			
			//check if question type as FK is exist in DB
			QuestionType qt =  questionTypeDao.findById(question.getQuestionType().getQuestionTypeId());
			valFkNotNull(qt);
			
			//Check if nonBK not null
			valNonBk(question);
			
			//Save
			return questionDao.save(question);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateQuestion(Question question) throws Exception{
		try {
			//Get old data of question from db
			Question oldQ = findById(question.getQuestionId());
			
			//Check if id not null
			valIdNotNull(question);
			
			//Check if id is exist in DB
			valIdExist(oldQ.getQuestionId());
			
			//Check if question title not null
			valBkNotNull(question);
			
			//Check if question title not being replaced
			valBkNotChange(oldQ, question);
			
			//Check if nonBK not null
			valNonBk(question);
			
			//save
			questionDao.save(question);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteQuestion(String id) throws Exception{
		try {
			//Check if id exist in DB
			valIdExist(id);
			
			//delete
			questionDao.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	// VALIDASI POST
	
 	private static Exception valIdNull(Question q) throws Exception{
 		if(q.getQuestionId() !=null ) {
 			throw new Exception("Id harus kosong");
 		}
 		return null;
 	}
 	
 	private static Exception valBkNotNull(Question q) throws Exception{
 		if (q.getQuestionTitle() == null || q.getQuestionTitle().trim().equals("") ) 
 		{
 			throw new Exception("UC tidak boleh kosong");
 		}
 		return null;
 	}
 	
 	private static Exception valBkNotExist(Question q) throws Exception{		
 		if(q.getQuestionId()!=null) {
 			throw new Exception("Question sudah terdaftar");
 		}
 		return null;
 	}
 	
 	private static Exception valFkNotNull(QuestionType qt) throws Exception{
 		if (qt.getQuestionTypeId() == null) 
 		{
 			throw new Exception("Question Type tidak ditemukan");
 		}
 		return null;
 	}
 	
 	private static Exception valNonBk(Question q) throws Exception{
 		if( q.getQuestionDesc() == null || q.getQuestionDesc().trim().equals("")
 				|| q.getTimestamp() == null
 				|| q.getActiveState() == null || q.getActiveState().trim().equals("")) 
 		{
 			throw new Exception("Masih ada field yang kosong");
 		}
 		return null;
 	}
 	
 	// VAlIDASI PUT (valIdNotNull,valIdExist, valBkNotNull, valBkNotChange, valNonBk)
 	
 	private static Exception valIdNotNull(Question q) throws Exception{
 		if(q.getQuestionId() == null || q.getQuestionId().trim().equals("")) {
 			throw new Exception("Id tidak boleh kosong");
 		}
 		return null;
 	}
 	
 	private static Exception valIdExist(String id) throws Exception{
 		if(id == null) {
 			throw new Exception("Question tidak ditemukan!");
 		}
 		return null;
 	}
 	
 	private static Exception valBkNotChange(Question oldQ, Question newQ) throws Exception{
 		if( !oldQ.getQuestionTitle().equalsIgnoreCase(newQ.getQuestionTitle())) {
 			throw new Exception("UC tidak dapat diubah!");
 		}
 		return null;
 	}
 	
 	// VALIDASI DELETE ( valIdExist )
	
}
