package com.linov.psikotes.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.linov.psikotes.entity.HeaderApplicantAnswer;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.HeaderAppAnsService;

@RestController
@RequestMapping("/applicant-answer")
public class HeaderAppAnsController {

	@Autowired
	private HeaderAppAnsService appAnsService;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody HeaderApplicantAnswer appAns) throws ErrorException{
		try {
			Date date =new Date();  
			appAns.setTimestamp(date);
			appAns.setTotalPoints(0);
			appAns.setStatus("Belum Mengerjakan");
			appAnsService.insertHeaderApplicantAnswer(appAns);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Jawaban kandidat berhasil ditambah");
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody HeaderApplicantAnswer appAns) throws ErrorException{
		try {
			appAnsService.updateHeaderApplicantAnswer(appAns);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Jawaban Kandidat Berhasil Diperbarui");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			appAnsService.deleteHeaderApplicantAnswer(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Jawaban kandidat Berhasil Dihapus");
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		return ResponseEntity.ok(appAnsService.findById(id));
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllUser() throws ErrorException {
		return ResponseEntity.ok(appAnsService.getAllHeaderApplicantAnswer());
	}
	
}
