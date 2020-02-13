package com.linov.psikotes.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.UserDao;
import com.linov.psikotes.entity.User;
import com.linov.psikotes.pojo.PojoUser;

@Service("loginService")
public class LoginService implements UserDetailsService{

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
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User theUser = userDao.loadUserByUsername(username);
    	if(theUser.getUserId() == null) {
    		throw new UsernameNotFoundException("User not found with username: " + username);
    	}
    	return new org.springframework.security.core.userdetails.User(theUser.getUsername(), theUser.getPassword(),
				new ArrayList<>());
    }
}
