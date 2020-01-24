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

import com.linov.psikotes.entity.PackageQuestion;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.PackageQuestionService;

@RestController
@RequestMapping("/question-pack")
@CrossOrigin("*")
public class PackageQuestionController {

	@Autowired
	private PackageQuestionService pqService;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody List<PackageQuestion> pq) throws ErrorException{
		try {
			for (PackageQuestion data : pq) {
				pqService.insertPq(data);
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Status: 201 Created");
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody PackageQuestion pq) throws ErrorException{
		try {
			pqService.updatePq(pq);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			pqService.deletePq(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(pqService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/package/id/{id}")
	public ResponseEntity<?> getByPackageId(@PathVariable String id) throws ErrorException {
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(pqService.findByPackageId(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllQuestPack() throws ErrorException {
		try {
//			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(pqService.getAllPq());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
