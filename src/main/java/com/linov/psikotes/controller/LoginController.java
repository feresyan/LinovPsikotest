package com.linov.psikotes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linov.psikotes.config.JwtTokenUtil;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.LoginService;
import com.linov.psikotes.service.UserService;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("")
	public ResponseEntity<?> login(@RequestBody Map<String,String> data) throws ErrorException{
		try {
			
			Object obj = new Object();
			List<Object> objs = new ArrayList<>();
			obj = userService.findByUsername(data.get("username"));
			objs.add(obj);
			
			authenticate(data.get("username"), data.get("password"));
			final UserDetails userDetails = loginService.loadUserByUsername(data.get("username"));
			final String token = jwtTokenUtil.generateToken(userDetails);
			objs.add(token);
			return ResponseEntity.ok(objs);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Gagal Login");
		}
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
