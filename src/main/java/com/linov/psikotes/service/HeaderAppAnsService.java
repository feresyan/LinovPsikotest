package com.linov.psikotes.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.HeaderAppAnsDao;
import com.linov.psikotes.dao.UserDao;
import com.linov.psikotes.entity.HeaderApplicantAnswer;
import com.linov.psikotes.entity.User;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.pojo.PojoSearchHeaderAppAns;

@Service("headerAppAnsService")
public class HeaderAppAnsService {

	@Autowired
	private HeaderAppAnsDao appAnsDao;
	
	@Autowired
	private UserDao userDao;

	public List<HeaderApplicantAnswer> getAllHeaderApplicantAnswer(){
		List<HeaderApplicantAnswer> list = appAnsDao.getAll();
		return list;
	}
	
	public HeaderApplicantAnswer findById(String id) {
		HeaderApplicantAnswer appAns = appAnsDao.findById(id);
		return appAns;
	}
	
	public HeaderApplicantAnswer findByUser(String id) {
		HeaderApplicantAnswer appAns = appAnsDao.findByUser(id);
		return appAns;
	}
	
	public List<HeaderApplicantAnswer> search(PojoSearchHeaderAppAns head) {
		List<HeaderApplicantAnswer> appAns = appAnsDao.search(head);
		return appAns;
	}
	
	public HeaderApplicantAnswer insertHeaderApplicantAnswer(HeaderApplicantAnswer appAns) throws Exception{
		try {
			
			//set all field
			Date date =new Date();  
			appAns.setTimestamp(date);
			appAns.setTotalPoints(0);
			appAns.setStatus("Belum Mengerjakan");
			appAns.setActiveState("active");
			
			//Check if id null 
			valIdNull(appAns);
			
			//Check if user null or not
			valBkNotNull(appAns);
			
			//Check if user not exist in DB 
			valBkNotExist(findByUser(appAns.getUser().getUserId()));
			
			//Check if user as FK is exist in DB
			User user = userDao.findById(appAns.getUser().getUserId());
			valFkUser(user);
			
			//Check if nonBK null or not
			valNonBk(appAns);
			
			//Save
			return appAnsDao.save(appAns);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateHeaderApplicantAnswer(HeaderApplicantAnswer appAns) throws Exception{
		try {
			//Get old data of HeaderApplicant Answer
			HeaderApplicantAnswer oldAppAns = findById(appAns.getApplicantAnswerId());
			
			//Check if appAnsId null or not
			valIdNotNull(appAns);
			
			//check if id already exist in DB
			valIdExist(oldAppAns.getApplicantAnswerId());
			
			//Check if user not null
			valBkNotNull(appAns);
			
			//Check if user not getting replaced
			valBkNotChange(oldAppAns, appAns);
			
			//Check if nonBk null or not
			valNonBk(appAns);
			
			//Save
			appAnsDao.save(appAns);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteHeaderApplicantAnswer(String id) throws ErrorException{
		try {
			//Check if id exist in DB
			valIdExist(id);
			
			//delete
			appAnsDao.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	// VALIDASI POST
	
	private static Exception valIdNull(HeaderApplicantAnswer appAns) throws Exception{
		if(appAns.getApplicantAnswerId() != null) {
			throw new Exception("Id harus kosong");
		}
		return null;
	}
	
	private static Exception valBkNotNull(HeaderApplicantAnswer appAns) throws Exception{
		if(appAns.getUser().getUserId()==null || appAns.getUser().getUserId().trim().equals("") ) {
			throw new Exception("User tidak boleh kosong");
		}
		return null;
	}
	
	private static Exception valBkNotExist(HeaderApplicantAnswer appAns) throws Exception{		
		if(appAns.getApplicantAnswerId()!=null) {
			throw new Exception("User sudah mengikuti tes");
		}
		return null;
	}
	
	private static Exception valNonBk(HeaderApplicantAnswer appAns) throws Exception{
		if( appAns.getTimestamp() == null || appAns.getTimestamp().toString().trim().equals("")
				|| appAns.getTotalPoints() == null || appAns.getTotalPoints().toString().trim().equals("")
				|| appAns.getActiveState() == null || appAns.getActiveState().trim().equals("")
				|| appAns.getStatus() == null || appAns.getStatus().trim().equals("")) {
			throw new Exception("Tidak boleh ada field yang kosong");
		}
		return null;
	}
	
	private static Exception valFkUser(User user) throws Exception{
		if(user.getUserId() == null) {
			throw new Exception("User tidak terdaftar!");
		}
		return null;
	}
	
	// VAlIDASI PUT (valIdNotNull,valIdExist, valBkNotNull, valBkNotChange, valNonBk)
	
	private static Exception valIdNotNull(HeaderApplicantAnswer appAns) throws Exception{
		if(appAns.getApplicantAnswerId() == null || appAns.getApplicantAnswerId().trim().equals("")) {
			throw new Exception("Id tidak boleh kosong");
		}
		return null;
	}
	
	private static Exception valIdExist(String id) throws Exception{
		if(id == null) {
			throw new Exception("Record tidak ditemukan!");
		}
		return null;
	}
	
	private static Exception valBkNotChange(HeaderApplicantAnswer oldAppAns, HeaderApplicantAnswer newAppAns) throws Exception{
		if(!oldAppAns.getUser().getUserId().equalsIgnoreCase(newAppAns.getUser().getUserId())) {
			throw new Exception("UC tidak dapat diubah!");
		}
		return null;
	}
	
	// VALIDASI DELETE ( valIdExist )
	
}
