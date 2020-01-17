package com.linov.psikotes.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linov.psikotes.entity.Answer;
import com.linov.psikotes.entity.Choice;
import com.linov.psikotes.entity.ListImg;
import com.linov.psikotes.entity.Question;
import com.linov.psikotes.entity.QuestionType;
import com.linov.psikotes.entity.SearchQuestion;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	 private static String UPLOADED_FOLDER = "D://BootCamp//Project Akhir//Project//psikotes//src//main//resources//img//question//";
	
	@PostMapping("/img")
	public ResponseEntity<?> insertImg(
			@RequestPart String questionTypeId,
			@RequestPart String questionTitle,
			@RequestPart String questionDesc,
			@RequestPart MultipartFile[] listImage,
			@RequestPart MultipartFile[] choice, 
			@RequestPart MultipartFile[] correctAnswer,
			@RequestPart String activeState) throws ErrorException{
		try {
			Question question = new Question();
			QuestionType qt = new QuestionType();
			Choice c = new Choice();
			Answer a = new Answer();
			ListImg l = new ListImg();
			
			//ListImage
			for (int i = 0; i < listImage.length; i++) {
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

			
			//choice image
			for (int i = 0; i < choice.length; i++) {
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
			
			//correct answer image
			for (int i = 0; i < correctAnswer.length; i++) {
				byte[] byteQuestion = correctAnswer[i].getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + correctAnswer[i].getOriginalFilename());
				Files.write(path, byteQuestion);
				
				if(a.getAnswer1()==null) {
					a.setAnswer1(path.toString());
				}else if (a.getAnswer2()==null) {
					a.setAnswer2(path.toString());
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
			questionService.insertQuestion(question);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Question Berhasil Ditambah");
	}
	
	@PostMapping("/text")
	public ResponseEntity<?> insertText(@RequestBody Question question) throws ErrorException{
		try {
			Date date = new Date();
			question.setTimestamp(date);
			questionService.insertQuestion(question);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Question Berhasil Ditambah");
	}
	
	@PutMapping("/update/img")
	public ResponseEntity<?> updateImg(
			@RequestPart String questionId,
			@RequestPart String questionTypeId,
			@RequestPart String questionTitle,
			@RequestPart String questionDesc,
			@RequestPart MultipartFile[] listImage,
			@RequestPart MultipartFile[] choice, 
			@RequestPart MultipartFile[] correctAnswer,
			@RequestPart String activeState) throws ErrorException{
		try {
			
			Question question = questionService.findById(questionId);
			QuestionType qt = new QuestionType();
			Choice c = new Choice();
			Answer a = new Answer();
			ListImg l = new ListImg();
			
			//ListImage
			for (int i = 0; i < listImage.length; i++) {
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

			
			//choice image
			for (int i = 0; i < choice.length; i++) {
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
			
			//correct answer image
			for (int i = 0; i < correctAnswer.length; i++) {
				byte[] byteQuestion = correctAnswer[i].getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + correctAnswer[i].getOriginalFilename());
				Files.write(path, byteQuestion);
				
				if(a.getAnswer1()==null) {
					a.setAnswer1(path.toString());
				}else if (a.getAnswer2()==null) {
					a.setAnswer2(path.toString());
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
			questionService.updateQuestion(question);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Question Berhasil Diupdate");
	}
	
	@PutMapping("/update/text")
	public ResponseEntity<?> update(@RequestBody Question question) throws ErrorException{
		try {
			questionService.updateQuestion(question);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Question Berhasil Diperbarui");
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			questionService.deleteQuestion(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Question Berhasil Dihapus");
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		return ResponseEntity.ok(questionService.findById(id));
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestBody SearchQuestion searchQuest) throws ErrorException {
		return ResponseEntity.ok(questionService.search(searchQuest));
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllQuestion() throws ErrorException {
		return ResponseEntity.ok(questionService.getAllQuestion());
	}
	
}
