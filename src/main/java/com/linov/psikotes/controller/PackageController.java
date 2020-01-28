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

import com.linov.psikotes.entity.Package;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.PackageService;

@RestController
@RequestMapping("/package")
@CrossOrigin("*")
public class PackageController {

	@Autowired
	private PackageService packageService;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody Package pack) throws ErrorException{
		try {
			packageService.insertPackage(pack);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(pack);
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody Package pack) throws ErrorException{
		try {
			packageService.updatePackage(pack);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(pack);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			packageService.deletePackage(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(packageService.findById(id));
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {		
		try {
			Object obj = "Status: 200 OK";
			ResponseEntity.status(HttpStatus.OK).body(obj);
			return ResponseEntity.ok(packageService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllPackage() throws ErrorException {
		try {
			Object obj = "Status: 200 OK";
			ResponseEntity.status(HttpStatus.OK).body(obj);
			return ResponseEntity.ok(packageService.getAllPackage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
