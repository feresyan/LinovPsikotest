package com.linov.psikotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.ReportService;


@RestController
@RequestMapping("/report")
@CrossOrigin("*")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/candidate/{id}/{format}")
	public ResponseEntity<?> getReportByHeaderId(@PathVariable String id, @PathVariable String format) throws ErrorException {
		
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(reportService.candidateReport(format, id));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/question/correct/{format}")
	public ResponseEntity<?> getReportCorrectAnswer(@PathVariable String format) throws ErrorException {
		
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(reportService.reportCorrectQuestion(format));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/question/wrong/{format}")
	public ResponseEntity<?> getReportFalseAnswer(@PathVariable String format) throws ErrorException {
		
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(reportService.reportFalseQuestion(format));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/package-question/correct/{format}")
	public ResponseEntity<?> getReportCorrectAnsAtPack(@PathVariable String format) throws ErrorException {
		
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(reportService.reportCorrectAnsAtPack(format));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/package-qustion/wrong/{format}")
	public ResponseEntity<?> getReportWrongAnsAtPack(@PathVariable String format) throws ErrorException {
		
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(reportService.reportWrongAnsAtPack(format));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/package/correct/{format}")
	public ResponseEntity<?> getPackageByTheMostCorrectAnswer(@PathVariable String format) throws ErrorException {
		
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(reportService.reportGetPackageByTheMostCorrectAnswer(format));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/package/wrong/{format}")
	public ResponseEntity<?> getPackageByTheMostWrongAnswer(@PathVariable String format) throws ErrorException {
		
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(reportService.reportGetPackageByTheMostWrongAnswer(format));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
