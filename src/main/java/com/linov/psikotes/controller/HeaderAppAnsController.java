package com.linov.psikotes.controller;

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

import com.linov.psikotes.entity.HeaderApplicantAnswer;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.pojo.PojoSearchHeaderAppAns;
import com.linov.psikotes.service.HeaderAppAnsService;

@RestController
@RequestMapping("/applicant-answer")
@CrossOrigin("*")
public class HeaderAppAnsController {

	@Autowired
	private HeaderAppAnsService appAnsService;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody HeaderApplicantAnswer appAns) throws ErrorException{
		try {
			appAnsService.insertHeaderApplicantAnswer(appAns);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(appAns);
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody HeaderApplicantAnswer appAns) throws ErrorException{
		try {
			appAnsService.updateHeaderApplicantAnswer(appAns);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(appAns);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			appAnsService.deleteHeaderApplicantAnswer(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(appAnsService.findById(id));
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		try {
			return ResponseEntity.ok(appAnsService.findById(id));			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/user/id/{id}")
	public ResponseEntity<?> getByUserId(@PathVariable String id) throws ErrorException {
		try {
			return ResponseEntity.ok(appAnsService.findByUser(id));			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody PojoSearchHeaderAppAns head) throws ErrorException {
		try {
			return ResponseEntity.ok(appAnsService.search(head));			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllHeader() throws ErrorException {
		try {
			return ResponseEntity.ok(appAnsService.getAllHeaderApplicantAnswer());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
