package com.linov.psikotes.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.linov.psikotes.dao.UserDao;
import com.linov.psikotes.entity.HeaderApplicantAnswer;
import com.linov.psikotes.entity.PojoSignUp;
import com.linov.psikotes.entity.PojoUser;
import com.linov.psikotes.entity.Profile;
import com.linov.psikotes.entity.Role;
import com.linov.psikotes.entity.User;
import com.linov.psikotes.pojo.PojoEmail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service("userService")
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HeaderAppAnsService headerService;
	
	@Autowired
	private ProfileService ps;
	
	@Autowired
	private RoleService rs;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@Autowired
	@Qualifier("emailConfigBean")
	private Configuration emailConfig;
	
	@Autowired
	public UserService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public List<PojoUser> getAllUser(){
		List<PojoUser> list = userDao.getAll();
		return list;
	}
	
	public User findById(String id) {
		User user = userDao.findById(id);
		return user;
	}
	
	
	public PojoUser findByUsername(String username) {
		PojoUser user = userDao.findByUsername(username);
		return user;
	}
	
	public PojoUser findByName(String name) {
		PojoUser user = userDao.findByProfileName(name);
		return user;
	}
	
	public PojoUser findByEmail(String email) {
		PojoUser user = userDao.findByEmail(email);
		return user;
	}
	
	public PojoUser findByPhone(String phone) {
		PojoUser user = userDao.findByPhone(phone);
		return user;
	}
	
	public void signUp(PojoSignUp pojoSignUp) throws Exception{
		//insert profile to DB
		Profile theProfile = ps.insertProfile(pojoSignUp.getProfile());
		
		//Set random password to new user
		User user = new User();
		String password = getRandomPassword(8);
		user.setPassword(password);
		
		//set role to new user
		user.setRole(rs.findByCode("candidate"));
		
		//set profile id to new user
		user.setProfile(theProfile);
		
		//set username to new user
		user.setUsername(pojoSignUp.getUsername());
		
		//set active state
		user.setActiveState("active");
		
		//Sending password via email candidate
		PojoEmail pojoEmail = new PojoEmail();
		pojoEmail.setPassword(password);
		pojoEmail.setSendTo(pojoSignUp.getProfile().getEmail());
		pojoEmail.setSubject("Linov Psikotest - Lawencon International");
		sendEmail(pojoEmail);
		
		//Set Timestamp
		Date date =new Date();  
		user.setTimestamp(date);
		
		//insert user to DB
		insertUser(user);
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
			
			//Encrypt password
			user.setPassword(passwordEncoder().encode(user.getPassword()));
			
			//save
			userDao.save(user);
			
			//Make header answer
			HeaderApplicantAnswer head = new HeaderApplicantAnswer();
			head.setUser(user);
			headerService.insertHeaderApplicantAnswer(head);
			
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
    
    //Encrypt password
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    //send email
    public void sendEmail(PojoEmail pojoEmail) throws MessagingException, IOException, TemplateException {

        Map<String, String> model = new HashMap<String, String>();
        model.put("password", pojoEmail.getPassword());
        /**
         * Add below line if you need to create a token to verification emails and uncomment line:32 in "email.ftl"
         * model.put("token",UUID.randomUUID().toString());
         */

        pojoEmail.setModel(model);


        //log.info("Sending Email to: " + mailModel.getTo());


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Template template = emailConfig.getTemplate("email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, pojoEmail.getModel());

        mimeMessageHelper.setTo(pojoEmail.getSendTo());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject(pojoEmail.getSubject());
        mimeMessageHelper.setFrom("no-reply@gmail.com");


        javaMailSender.send(message);

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
