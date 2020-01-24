package com.linov.psikotes.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.linov.psikotes.dao.QuestionDao;
import com.linov.psikotes.dao.QuestionTypeDao;
import com.linov.psikotes.entity.Answer;
import com.linov.psikotes.entity.Choice;
import com.linov.psikotes.entity.ListImg;
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
	
	public List<Question> getChoice(){
		List<Question> list = questionDao.findFileName();
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
	
	public void insertQuestionImg(
				 String UPLOADED_FOLDER,
				 String questionTypeId,
				 String questionTitle,
				 String questionDesc,
				 MultipartFile[] listImage,
				 MultipartFile[] choice, 
				 MultipartFile[] correctAnswer,
				 String activeState
			) throws Exception 
	{
		Question question = new Question();
		QuestionType qt = new QuestionType();
		Choice c = new Choice();
		Answer a = new Answer();
		ListImg l = new ListImg();
		
		//create directory
		Path p = Paths.get(UPLOADED_FOLDER);
		if(!Files.exists(p)) {
			try {
				Files.createDirectories(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//ListImage
		for (int i = 0; i < listImage.length; i++) {
			if (!listImage[i].isEmpty()) {
				
				//Check file extension
				valFileExt(listImage[i].getOriginalFilename());
				
				//Check file size
				valFileSize(listImage[i].getSize());
				
//				//Stringbuilder
//				StringBuilder newFileName = new StringBuilder();
//				
//				//Make random String for fileName
//				newFileName.append(getRandomPassword(20));
//				
//				//Get Extension File
//				String ext = FilenameUtils.getExtension(listImage[i].getOriginalFilename());
//				newFileName.append(ext.toString());
				
				byte[] byteQuestion2 = listImage[i].getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + listImage[i].getOriginalFilename());
								
				Files.write(path, byteQuestion2);
				
				if(l.getImgA()==null) {
					l.setImgA(path.toString());
				}else if (l.getImgB()==null) {
					l.setImgB(path.toString());
				}else if (l.getImgC()==null) {
					l.setImgC(path.toString());
				}else if (l.getImgD()==null) {
					l.setImgD(path.toString());
				}else if(l.getImgE()==null) {
					l.setImgE(path.toString());
				}else if(l.getImgF()==null) {
					l.setImgF(path.toString());
				}
			}
		}


		
		//choice image
		for (int i = 0; i < choice.length; i++) {
			if (!choice[i].isEmpty()) {
				
				//Check file extension
				valFileExt(choice[i].getOriginalFilename());
				
				//Check file size
				valFileSize(choice[i].getSize());
				
//				//Stringbuilder
//				StringBuilder newFileName = new StringBuilder();
//				
//				//Make random String for fileName
//				newFileName.append(getRandomPassword(20));
//				
//				//Get Extension File
//				String ext = FilenameUtils.getExtension(listImage[i].getOriginalFilename());
//				newFileName.append(ext.toString());
				
				byte[] byteQuestion2 = choice[i].getBytes();
				Path path2 = Paths.get(UPLOADED_FOLDER + choice[i].getOriginalFilename());
				Files.write(path2, byteQuestion2);
				
				if(c.getChoiceA()==null) {
					c.setChoiceA(path2.toString());
				}else if (c.getChoiceB()==null) {
					c.setChoiceB(path2.toString());
				}else if (c.getChoiceC()==null) {
					c.setChoiceC(path2.toString());
				}else if (c.getChoiceD()==null) {
					c.setChoiceD(path2.toString());
				}else if(c.getChoiceE()==null) {
					c.setChoiceE(path2.toString());
				}
			}
			else
			{
				throw new Exception("Pilihan harus diisi");
			}
		}
		
		//correct answer image
		for (int i = 0; i < correctAnswer.length; i++) {
			if (!correctAnswer[i].isEmpty()) {
				
				//Check file extension
				valFileExt(correctAnswer[i].getOriginalFilename());
				
				//Check file size
				valFileSize(correctAnswer[i].getSize());
				
//				//Stringbuilder
//				StringBuilder newFileName = new StringBuilder();
//				
//				//Make random String for fileName
//				newFileName.append(getRandomPassword(20));
//				
//				//Get Extension File
//				String ext = FilenameUtils.getExtension(listImage[i].getOriginalFilename());
//				newFileName.append(ext.toString());
				
				byte[] byteQuestion = correctAnswer[i].getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + correctAnswer[i].getOriginalFilename());
				Files.write(path, byteQuestion);
				
				if(a.getAnswer1()==null) {
					a.setAnswer1(path.toString());
				}else if (a.getAnswer2()==null) {
					a.setAnswer2(path.toString());
				}
			}
			else
			{
				throw new Exception("Jawaban harus diisi!");
			}
		}
			
		//set question type
		qt.setQuestionTypeId(questionTypeId);
		
		question.setQuestionType(qt);
		question.setQuestionTitle(questionTitle);
		question.setQuestionDesc(questionDesc);
		question.setChoice(c);
		question.setCorrectAnswer(a);
		question.setListImg(l);
		Date date = new Date();
		question.setTimestamp(date);
		question.setActiveState(activeState);
		insertQuestion(question);
	}
	
	public void updateQuestionImg(
				 String UPLOADED_FOLDER,
				 String questionId,
				 String questionTypeId,
				 String questionTitle,
				 String questionDesc,
				 MultipartFile[] listImage,
				 MultipartFile[] choice, 
				 MultipartFile[] correctAnswer,
				 String activeState
			) throws Exception
	{
		Question question = findById(questionId);
		QuestionType qt = new QuestionType();
		Choice c = new Choice();
		Answer a = new Answer();
		ListImg l = new ListImg();

		//ListImage
		for (int i = 0; i < listImage.length; i++) {
			if (!listImage[i].isEmpty()) {
				
				//Check file extension
				valFileExt(listImage[i].getOriginalFilename());
				
				//Check file size
				valFileSize(listImage[i].getSize());
				
				byte[] byteQuestion2 = listImage[i].getBytes();
				Path path3 = Paths.get(UPLOADED_FOLDER + listImage[i].getOriginalFilename());
				Files.write(path3, byteQuestion2);
				
				if(l.getImgA()==null) {
					l.setImgA(path3.toString());
				}else if (l.getImgB()==null) {
					l.setImgB(path3.toString());
				}else if (l.getImgC()==null) {
					l.setImgC(path3.toString());
				}else if (l.getImgD()==null) {
					l.setImgD(path3.toString());
				}else if(l.getImgE()==null) {
					l.setImgE(path3.toString());
				}else if(l.getImgF()==null) {
					l.setImgF(path3.toString());
				}
			}
			
		}

		
		//choice image
		for (int i = 0; i < choice.length; i++) {
			if (!choice[i].isEmpty()) {
				
				//Check file extension
				valFileExt(choice[i].getOriginalFilename());
				
				//Check file size
				valFileSize(choice[i].getSize());
				
				byte[] byteQuestion2 = choice[i].getBytes();
				Path path2 = Paths.get(UPLOADED_FOLDER + choice[i].getOriginalFilename());
				Files.write(path2, byteQuestion2);
				
				if(c.getChoiceA()==null) {
					c.setChoiceA(path2.toString());
				}else if (c.getChoiceB()==null) {
					c.setChoiceB(path2.toString());
				}else if (c.getChoiceC()==null) {
					c.setChoiceC(path2.toString());
				}else if (c.getChoiceD()==null) {
					c.setChoiceD(path2.toString());
				}else if(c.getChoiceE()==null) {
					c.setChoiceE(path2.toString());
				}
			}
			else
			{
				throw new Exception("Pertanyaan harus disii!");
			}
		}
		
		//correct answer image
		for (int i = 0; i < correctAnswer.length; i++) {
			if (!correctAnswer[i].isEmpty()) {
				
				//Check file extension
				valFileExt(correctAnswer[i].getOriginalFilename());
				
				//Check file size
				valFileSize(correctAnswer[i].getSize());
				
				byte[] byteQuestion = correctAnswer[i].getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + correctAnswer[i].getOriginalFilename());
				Files.write(path, byteQuestion);
				
				if(a.getAnswer1()==null) {
					a.setAnswer1(path.toString());
				}else if (a.getAnswer2()==null) {
					a.setAnswer2(path.toString());
				}
			} else {
				throw new Exception("Jawaban harus diisi");
			}
		}
			
		//set question type
		qt.setQuestionTypeId(questionTypeId);
		
		question.setQuestionType(qt);
		question.setQuestionTitle(questionTitle);
		question.setQuestionDesc(questionDesc);
		question.setChoice(c);
		question.setCorrectAnswer(a);
		question.setListImg(l);
		question.setActiveState(activeState);
		updateQuestion(question);
	}
	
	public static String getRandomPassword(int n) 
    { 
  
        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
  
        return sb.toString(); 
    }
	
	// VALIDASI POST
	
	private static Exception valFileExt(String fileName) throws Exception {
		String ext = FilenameUtils.getExtension(fileName);
		if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("png")) {
			return null;
		}
		throw new Exception("ekstensi file hanya jpg/png! ");
	}
	
	private static Exception valFileSize(Long byteFile) throws Exception {
		if((byteFile/1024) < 500) {
			return null;
		}
		throw new Exception("Maksimal besar file hanya 1 mb!");
	}
	
//	private static  String valFileName(Question question,String oriFileName) {
//		if(
//				question.getChoice().getChoiceA().equalsIgnoreCase(oriFileName) || 
//				question.getChoice().getChoiceB().equalsIgnoreCase(oriFileName) || 
//				question.getChoice().getChoiceC().equalsIgnoreCase(oriFileName) ||
//				question.getChoice().getChoiceD().equalsIgnoreCase(oriFileName) ||
//				question.getCorrectAnswer().getAnswer1().equalsIgnoreCase(oriFileName) ||
//				question.getCorrectAnswer().getAnswer2().equalsIgnoreCase(oriFileName)
//				) 
//		{
//			return getRandomPassword(12);
//		}
//		return oriFileName;
//	}
	
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
