package com.linov.psikotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.UserDao;
import com.linov.psikotes.entity.PojoUser;
import com.linov.psikotes.entity.User;

@Service("loginService")
public class LoginService {

	@Autowired
	private UserDao userDao;
	
	public PojoUser login(String username, String password) throws Exception {
		User user = userDao.findByUsernameWithPassword(username);
		if(user != null ) {
			if (user.getPassword().equalsIgnoreCase(password)) {
				PojoUser pu = new PojoUser();
				pu.setUserId(user.getUserId());
				pu.setUsername(user.getUsername());
				pu.setRole(user.getRole());
				pu.setProfile(user.getProfile());
				pu.setTimestamp(user.getTimestamp());
				pu.setActiveState(user.getActiveState());
				return pu;
			}
			else {
				throw new Exception("Status: 400 Bad Request password not found");
			}
		} else {
			throw new Exception("Status: 400 Bad Request username not found");
		}
	}
}
