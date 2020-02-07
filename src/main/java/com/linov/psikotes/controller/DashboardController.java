package com.linov.psikotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;
	
	
	@GetMapping("/total-candidate")
	public ResponseEntity<?> getTotalCandidate() throws ErrorException {
		try {
			return ResponseEntity.ok(dashboardService.getTotalCandidate());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/total-candidate-pass")
	public ResponseEntity<?> getTotalCandidateThatPass() throws ErrorException {
		try {
			return ResponseEntity.ok(dashboardService.getTotalCandidateThatPass());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/total-candidate-not-pass")
	public ResponseEntity<?> getTotalCandidateThatNotPass() throws ErrorException {
		try {
			return ResponseEntity.ok(dashboardService.getTotalCandidateThatNotPass());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/total-question")
	public ResponseEntity<?> getTotalQuestion() throws ErrorException {
		try {
			return ResponseEntity.ok(dashboardService.getTotalQuestion());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
