package com.linov.psikotes.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.linov.psikotes.entity.QuestionType;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.QuestionTypeService;

@RestController
@RequestMapping("/question-type")
public class QuestionTypeController {
	
	@Autowired
	private QuestionTypeService questionTypeService;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody QuestionType questionType) throws ErrorException{
		try {
			questionTypeService.insertQuestionType(questionType);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Status: 201 Created");
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody QuestionType questionType) throws ErrorException{
		try {
			questionTypeService.updateQuestionType(questionType);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			questionTypeService.deleteQuestionType(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(questionTypeService.findById(id));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}		
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllQuestionType() throws ErrorException {
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(questionTypeService.getAllQuestionType());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
		
}
