package com.linov.psikotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linov.psikotes.entity.Profile;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
//	@PostMapping("")
//	public ResponseEntity<?> insert(@RequestBody Profile profile) throws ErrorException{
//		try {
//			profileService.insertProfile(profile);
//		}catch(Exception e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//		}
//		return ResponseEntity.status(HttpStatus.CREATED).body("Profile Berhasil Ditambah");
//	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody Profile profile) throws ErrorException{
		try {
			profileService.updateProfile(profile);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Profile Berhasil Diperbarui");
	}
	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
//		try {
//			profileService.deleteProfile(id);
//		}catch(Exception e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//		}
//		return ResponseEntity.status(HttpStatus.OK).body("Profile Berhasil Dihapus");
//	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(profileService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/search/name/{name}")
	public ResponseEntity<?> getByName(@PathVariable String name) throws ErrorException {
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(profileService.findByName(name));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
	
	@GetMapping("/search/email/{email}")
	public ResponseEntity<?> getByEmail(@PathVariable String email) throws ErrorException {
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(profileService.findByEmail(email));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/search/phone/{phone}")
	public ResponseEntity<?> getByPhone(@PathVariable String phone) throws ErrorException {
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(profileService.findByPhone(phone));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllProfile() throws ErrorException {
		try {
			ResponseEntity.status(HttpStatus.OK).body("Status: 200 OK");
			return ResponseEntity.ok(profileService.getAllProfile());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
