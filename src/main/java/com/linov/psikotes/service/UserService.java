package com.linov.psikotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.UserDao;
import com.linov.psikotes.entity.Profile;
import com.linov.psikotes.entity.Role;
import com.linov.psikotes.entity.User;

@Service("userService")
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProfileService ps;
	
	@Autowired
	private RoleService rs;
	
	@Autowired
	private JavaMailSender javaMailSender;

	public List<User> getAllRole(){
		List<User> list = userDao.getAll();
		return list;
	}
	
	public User findById(String id) {
		User user = userDao.findById(id);
		return user;
	}
	
	public User findByUsername(String username) {
		User user = userDao.findByUsername(username);
		return user;
	}
	
	public void insertUser(User user) throws Exception{
		try {
			//Check if user id is null
			valIdNull(user);
			
			//Check if password and profileId are not null
			valBkNotNull(user);
			
			//Check if password or profileId are not in DB yet
			User userResult = findUserByBK(user);
			valBkNotExist(userResult);
			
			//Check if profileId is exist in DB
			Profile profileResult = ps.findById(user.getProfile().getProfileId()); 
			valProfileId(profileResult);
			
			//Check if roleId is exist in DB
			Role roleResult = rs.findById(user.getRole().getRoleId());
			valRoleId(roleResult);
			
			//Check if nonBK null or not
			valNonBk(user);
			
			//save
			userDao.save(user);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateUser(User user) throws Exception{
		try {
			//Get OldData of user
			User oldUser = findById(user.getUserId());
			
			//Check if id is null or not from controller
			valIdNotNull(user);
			
			//Check if id is exist in DB
			valIdExist(oldUser.getUserId());
			
			//Check if password and profileId are not null
			valBkNotNull(user);
			
			//Check if profileId is changing or not
			valBkNotChange(oldUser, user);
					
			//Check if roleId is exist in DB
			Role roleResult = rs.findById(user.getRole().getRoleId());
			valRoleId(roleResult);
			
			//Check if nonBK null or not
			valNonBk(user);
			
			//save
			userDao.save(user);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteUser(String id) throws Exception{
		try {
			//Check if userId is exist in DB
			valIdExist(id);
			
			//delete
			userDao.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public User findUserByBK(User user) {
		return userDao.findBK(user.getPassword(),user.getProfile().getProfileId());
	}
	
	 // function to generate a random string of length n 
    public String getRandomPassword(int n) 
    { 
  
        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
  
        return sb.toString(); 
    }
    
    public void sendEmail(String setTo,String password) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(setTo);
		msg.setSubject("Linov Psikotest");
		msg.setText("Your Password is : " + password + 
				"\n\n Your password is your responsibility" 
				+"\n\n Best Regards \n\n\n Linov HR");
		javaMailSender.send(msg);
	}
    
 // VALIDASI POST
	
 	private static Exception valIdNull(User user) throws Exception{
 		if(user.getUserId() != null ) {
 			throw new Exception("Id harus kosong");
 		}
 		return null;
 	}
 	
 	private static Exception valBkNotNull(User user) throws Exception{
 		if  (user.getPassword() == null || 	user.getPassword().trim().equals("")
 				|| 	user.getProfile().getProfileId() == null || 	user.getProfile().getProfileId().trim().equals("")
 				|| user.getUsername() == null || user.getUsername().trim().equals("")) 
 		{
 			throw new Exception("UC tidak boleh kosong");
 		}
 		return null;
 	}
 	
 	private static Exception valBkNotExist(User user) throws Exception{		
 		if(user.getUserId()!=null) {
 			throw new Exception("password / profileId sudah terdaftar");
 		}
 		return null;
 	}
 	
 	private static Exception valProfileId(Profile profile) throws Exception{		
 		if(profile.getProfileId()==null) {
 			throw new Exception("Profile tidak terdaftar");
 		}
 		return null;
 	}
 	
 	private static Exception valRoleId(Role role) throws Exception{		
 		if(role.getRoleId()==null) {
 			throw new Exception("Role tidak terdaftar");
 		}
 		return null;
 	}
 	
 	private static Exception valNonBk(User user) throws Exception{
 		if( user.getActiveState() == null  || user.getActiveState().trim().equals("")
 				|| user.getRole().getRoleId() ==null || user.getRole().getRoleId().trim().equals("")
 				|| user.getTimestamp() == null || user.getTimestamp().toString().trim().equals("")) 
 		{
 			throw new Exception("Tidak boleh ada field yang kosong");
 		}
 		return null;
 	}
 	
 	// VAlIDASI PUT (valIdNotNull,valIdExist, valBkNotNull, valBkNotChange, valNonBk)
 	
 	private static Exception valIdNotNull(User user) throws Exception{
 		if(user.getUserId()==null || user.getUserId().trim().equals("")) {
 			throw new Exception("Id tidak boleh kosong");
 		}
 		return null;
 	}
 	
 	private static Exception valIdExist(String id) throws Exception{
 		if(id == null) {
 			throw new Exception("User tidak ditemukan!");
 		}
 		return null;
 	}
 	
 	private static Exception valBkNotChange(User oldUser, User newUser) throws Exception{
 		if(!oldUser.getProfile().getProfileId().equalsIgnoreCase(newUser.getProfile().getProfileId())) {
 			throw new Exception("UC tidak dapat diubah!");
 		}
 		return null;
 	}
 	
 	// VALIDASI DELETE ( valIdExist )
	
}
