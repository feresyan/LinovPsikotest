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
@RequestMapping("/report/candidate")
@CrossOrigin("*")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/{id}/{format}")
	public ResponseEntity<?> getReportByHeaderId(@PathVariable String id, @PathVariable String format) throws ErrorException {
		
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(reportService.candidateReport(format, id));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
