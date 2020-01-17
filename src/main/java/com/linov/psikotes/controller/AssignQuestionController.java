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

import com.linov.psikotes.entity.AssignQuestion;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.AssignQuestionService;

@RestController
@RequestMapping("/assign-question")
public class AssignQuestionController {

	@Autowired
	private AssignQuestionService aqService;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody AssignQuestion aq) throws ErrorException{
		try {
			aqService.insertAq(aq);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("AssignQuestion Berhasil Ditambah");
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody AssignQuestion aq) throws ErrorException{
		try {
			aqService.updateAq(aq);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("AssignQuestion Berhasil Diperbarui");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			aqService.deleteAq(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("AssignQuestion Berhasil Dihapus");
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		return ResponseEntity.ok(aqService.findById(id));
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllUser() throws ErrorException {
		return ResponseEntity.ok(aqService.getAllAq());
	}
	
}
