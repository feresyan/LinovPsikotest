package com.linov.psikotes.controller;

import java.util.Map;

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

import com.linov.psikotes.entity.User;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.pojo.PojoSignUp;
import com.linov.psikotes.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody PojoSignUp pojoSignUp) throws ErrorException{
		try {
			userService.signUp(pojoSignUp);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(pojoSignUp);
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody User user) throws ErrorException{
		try {
			User oldUser = userService.findById(user.getUserId());
			user.setTimestamp(oldUser.getTimestamp());
			userService.updateUser(user);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			userService.deleteUser(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		try {
			return ResponseEntity.ok(userService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
		
	@GetMapping("/username/{username}")
	public ResponseEntity<?> getByUsername(@PathVariable String username) throws ErrorException {
		try {
			return ResponseEntity.ok(userService.findByUsername(username));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/search/name/{name}")
	public ResponseEntity<?> getByProfileName(@PathVariable String name) throws ErrorException {
		try {
			return ResponseEntity.ok(userService.findByName(name));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/search/email/{email}")
	public ResponseEntity<?> getByEmail(@PathVariable String email) throws ErrorException {
		try {
			return ResponseEntity.ok(userService.findByEmail(email));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/search/phone/{phone}")
	public ResponseEntity<?> getByPhone(@PathVariable String phone) throws ErrorException {
		try {
			return ResponseEntity.ok(userService.findByPhone(phone));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/check-password")
	public ResponseEntity<?> checkPassword(@RequestBody Map<String,String> data) throws ErrorException {
		try {
			String userId = data.get("userId");
			String password = data.get("password");
			return ResponseEntity.ok(userService.checkPassword(userId,password));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
		
	@GetMapping("")
	public ResponseEntity<?> getAllUser() throws ErrorException {
		try {
			return ResponseEntity.ok(userService.getAllUser());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	

}
