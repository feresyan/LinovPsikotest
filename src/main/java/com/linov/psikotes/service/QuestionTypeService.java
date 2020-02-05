package com.linov.psikotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.QuestionTypeDao;
import com.linov.psikotes.entity.QuestionType;

@Service("questionTypeService")
public class QuestionTypeService {

	@Autowired
	private QuestionTypeDao questionTypeDao;

	public List<QuestionType> getAllQuestionType(){
		List<QuestionType> list = questionTypeDao.getAll();
		return list;
	}
	
	public QuestionType findById(String id) {
		QuestionType qt = questionTypeDao.findById(id);
		return qt;
	}
	
	public QuestionType findByName(String title) {
		QuestionType qt = questionTypeDao.findByName(title);
		return qt;
	}
	
	public QuestionType insertQuestionType(QuestionType questionType) throws Exception{
		try {
			
			QuestionType qt = questionTypeDao.findByTitle(questionType.getQuestionTypeTitle());
			if(qt.getQuestionTypeTitle() != null) {
				qt.setActiveState("active");
				updateQuestionType(qt);
				return qt;
			} else {
				//Check if questionTypeId is null
				valIdNull(questionType);
				
				//Check if question type title is not null
				valBkNotNull(questionType);
				
				//Check if question type title is not exist in DB
				valBkNotExist(questionType);
				
				//Check if nonBk not null
				valNonBk(questionType);
				
				//Save
				return questionTypeDao.save(questionType);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateQuestionType(QuestionType questionType) throws Exception{
		try {
			//Get old data of question type from DB
			QuestionType oldQt = findById(questionType.getQuestionTypeId());
			
			//Check if id not null
			valIdNotNull(questionType);
			
			//Check if id is exist in DB
			valIdExist(oldQt.getQuestionTypeId());
			
			//Check if question title not null
			valBkNotNull(questionType);
			
			//Check if question title not being replaced
//			valBkNotChange(oldQt, questionType);
			
			//Check if nonBK not null
			valNonBk(questionType);
			
			//Save
			questionTypeDao.save(questionType);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteQuestionType(String id) throws Exception{
		try {
			//Check if id is exist in DB 
			valIdExist(id);
			//delete
			questionTypeDao.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public QuestionType findByTitle(QuestionType questionType) {
		return questionTypeDao.findByTitle(questionType.getQuestionTypeTitle());
	}
	
	// VALIDASI POST
	
	 	private static Exception valIdNull(QuestionType qt) throws Exception{
	 		if(qt.getQuestionTypeId() !=null ) {
	 			throw new Exception("Id harus kosong");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valBkNotNull(QuestionType qt) throws Exception{
	 		if (qt.getQuestionTypeTitle() == null || qt.getQuestionTypeTitle().trim().equals("") ) 
	 		{
	 			throw new Exception("UC tidak boleh kosong");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valBkNotExist(QuestionType qt) throws Exception{		
	 		if(qt.getQuestionTypeId()!=null) {
	 			throw new Exception("Question type sudah terdaftar");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valNonBk(QuestionType qt) throws Exception{
	 		if( qt.getActiveState() == null || qt.getActiveState().trim().equals("")) 
	 		{
	 			throw new Exception("Tidak boleh ada field yang kosong");
	 		}
	 		return null;
	 	}
	 	
	 	// VAlIDASI PUT (valIdNotNull,valIdExist, valBkNotNull, valBkNotChange, valNonBk)
	 	
	 	private static Exception valIdNotNull(QuestionType qt) throws Exception{
	 		if(qt.getQuestionTypeId() == null || qt.getQuestionTypeId().trim().equals("")) {
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
	 	
//	 	private static Exception valBkNotChange(QuestionType oldQt, QuestionType newQt) throws Exception{
//	 		if( !oldQt.getQuestionTypeTitle().equalsIgnoreCase(newQt.getQuestionTypeTitle())) {
//	 			throw new Exception("UC tidak dapat diubah!");
//	 		}
//	 		return null;
//	 	}
	 	
	 	// VALIDASI DELETE ( valIdExist )
}
