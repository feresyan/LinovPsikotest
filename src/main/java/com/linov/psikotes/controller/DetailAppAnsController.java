package com.linov.psikotes.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.linov.psikotes.entity.DetailApplicantAnswer;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.DetailAppAnsService;

@RestController
@RequestMapping("/applicant-answer/detail")
@CrossOrigin("*")
public class DetailAppAnsController {
	
	@Autowired
	private DetailAppAnsService dAppAnsService;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody List<DetailApplicantAnswer> dAppAns) throws ErrorException{
		try {
			//Calculate and insert the answer
			dAppAnsService.calculatePoints(dAppAns);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(dAppAns);
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody DetailApplicantAnswer dAppAns) throws ErrorException{
		try {
			dAppAnsService.updateDetail(dAppAns);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(dAppAns);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			dAppAnsService.deleteDetail(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(dAppAnsService.findById(id));
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		return ResponseEntity.ok(dAppAnsService.findById(id));
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getAllDetailByHeaderId(@PathVariable String id) throws ErrorException {
		return ResponseEntity.ok(dAppAnsService.getAllDetailByAppAnsId(id));
	}
	
//	@GetMapping("/essay/user/{id}")
//	public ResponseEntity<?> getAllEssayQuestByAppAnsId(@PathVariable String id) throws ErrorException {
//		return ResponseEntity.ok(dAppAnsService.getAllEssayQuestByAppAnsId(id));
//	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllDetail() throws ErrorException {
		return ResponseEntity.ok(dAppAnsService.getAllDetail());
	}
	
}
