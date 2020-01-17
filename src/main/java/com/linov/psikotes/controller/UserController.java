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

import com.linov.psikotes.entity.Profile;
import com.linov.psikotes.entity.User;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.ProfileService;
import com.linov.psikotes.service.RoleService;
import com.linov.psikotes.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody Profile profile) throws ErrorException{
		try {
			//insert profile to DB
			Profile theProfile = profileService.insertProfile(profile);
			
			//Set random password to new user
			User user = new User();
			String password = userService.getRandomPassword(8);
			user.setPassword(password);
			
			//set role to new user
			user.setRole(roleService.findByCode("candidate"));
			
			//set profile id to new user
			user.setProfile(theProfile);
			
			//set username to new user
			String username = userService.getRandomPassword(8);
			user.setUsername(username);
			
			//set active state
			user.setActiveState("active");
			
			//Sending password via email candidate
			userService.sendEmail(profile.getEmail(),password);
			
			//Set Timestamp
			Date date =new Date();  
			user.setTimestamp(date);
			
			//insert user to DB
			userService.insertUser(user);
			
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("User Berhasil Ditambah");
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
		return ResponseEntity.status(HttpStatus.OK).body("User Berhasil Diperbarui");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			userService.deleteUser(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("User Berhasil Dihapus");
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		return ResponseEntity.ok(userService.findById(id));
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<?> getByUsername(@PathVariable String username) throws ErrorException {
		return ResponseEntity.ok(userService.findByUsername(username));
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllUser() throws ErrorException {
		return ResponseEntity.ok(userService.getAllUser());
	}
	

}
