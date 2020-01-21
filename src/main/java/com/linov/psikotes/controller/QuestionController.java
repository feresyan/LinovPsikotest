package com.linov.psikotes.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.linov.psikotes.entity.Question;
import com.linov.psikotes.entity.SearchQuestion;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.QuestionService;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	 private static String UPLOADED_FOLDER = "D://BootCamp//Project Akhir//Project//psikotes//src//main//resources//img//question//";
	
//	@Value("uploaded.folder")
//	private String path;
	 
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
			//set all images and insert to DB
			questionService.insertQuestionImg(UPLOADED_FOLDER, questionTypeId, questionTitle, questionDesc, listImage, choice, correctAnswer, activeState);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Status: 201 Created");
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
		return ResponseEntity.status(HttpStatus.CREATED).body("Status: 201 Created");
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
			//update images question and update to DB
			questionService.updateQuestionImg(UPLOADED_FOLDER, questionId, questionTypeId, questionTitle, questionDesc, listImage, choice, correctAnswer, activeState);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Status: 200 Ok");
	}
	
	@PutMapping("/update/text")
	public ResponseEntity<?> updateText(@RequestBody Question question) throws ErrorException{
		try {
			questionService.updateQuestion(question);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Status: 200 Ok");
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			questionService.deleteQuestion(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Status: 200 Ok");
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		try {
			 ResponseEntity.status(HttpStatus.OK).body("Status: 200 Ok");
			return ResponseEntity.ok(questionService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestBody SearchQuestion searchQuest) throws ErrorException {
		try {
			 ResponseEntity.status(HttpStatus.OK).body("Status: 200 Ok");
			 return ResponseEntity.ok(questionService.search(searchQuest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllQuestion() throws ErrorException {
		try {
			 ResponseEntity.status(HttpStatus.OK).body("Status: 200 Ok");
			 return ResponseEntity.ok(questionService.getAllQuestion());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/choice")
	public ResponseEntity<?> getAllChoice() throws ErrorException {
		try {
			 ResponseEntity.status(HttpStatus.OK).body("Status: 200 Ok");
			 return ResponseEntity.ok(questionService.getChoice());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
