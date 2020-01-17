package com.linov.psikotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.ProfileDao;
import com.linov.psikotes.entity.Profile;

@Service("profileService")
public class ProfileService {

	@Autowired
	private ProfileDao profileDao;

	public List<Profile> getAllProfile(){
		List<Profile> list = profileDao.getAll();
		return list;
	}
	
	public List<Profile> findByName(String name){
		List<Profile> list = profileDao.findByName(name);
		return list;
	}
	
	public Profile findByEmail(String email){
		Profile profile = profileDao.findByEmail(email);
		return profile;
	}
	
	public Profile findByPhone(String phone){
		Profile profile = profileDao.findByPhone(phone);
		return profile;
	}
	
	public Profile findById(String id) {
		Profile profile = profileDao.findById(id);
		return profile;
	}
	
	public Profile insertProfile(Profile profile) throws Exception{
		try {
			
			//Check if profileId is null or not
			valIdNull(profile);
			
			//Check if phone and email are null or not
			valBkNotNull(profile);
			
			//Check if phone or email already exist in DB or not
			Profile profileResult = findBK(profile);
			valBkNotExist(profileResult);
			
			//Check if nonBK null or not
			valNonBk(profile);
			
			//Save
			return profileDao.save(profile);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateProfile(Profile profile) throws Exception{
		try {
			//Get old data of profile from DB
			Profile oldProfile = findById(profile.getProfileId());
			
			//Check if id is not null
			valIdNotNull(profile);
			
			//Check if id already exist in DB or not
			valIdExist(oldProfile.getProfileId());
			
			//Check if password and email are not null
			valBkNotNull(profile);
			
			//Check if phone or email being replaced 
//			valBkNotChange(oldProfile, profile);
			
			//Check if nonBK not null
			valNonBk(profile);
			
			//Save
			profileDao.save(profile);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteProfile(String id) throws Exception{
		try {
			//Check if profileId is exist in DB
			valIdExist(id);
			
			//delete
			profileDao.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public Profile findBK(Profile profile) {
		return profileDao.findBK(profile.getPhone(),profile.getEmail());
	}
	
	// VALIDASI POST
	
	 	private static Exception valIdNull(Profile profile) throws Exception{
	 		if(profile.getProfileId() != null) {
	 			throw new Exception("Id harus kosong");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valBkNotNull(Profile profile) throws Exception{
	 		if  (profile.getPhone() == null || 	profile.getPhone().trim().equals("")
	 				|| 	profile.getEmail() == null || 	profile.getEmail().trim().equals("")) 
	 		{
	 			throw new Exception("UC tidak boleh kosong");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valBkNotExist(Profile profile) throws Exception{		
	 		if(profile.getProfileId()!=null) {
	 			throw new Exception("profile sudah terdaftar");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valNonBk(Profile profile) throws Exception{
	 		if( profile.getProfileName() == null  || profile.getProfileName().trim().equals("")
	 				|| profile.getGender() ==null || profile.getGender().trim().equals("")
	 				|| profile.getDob() ==null 
	 				|| profile.getAddress() ==null || profile.getAddress().trim().equals("")) 
	 		{
	 			throw new Exception("Tidak boleh ada field yang kosong");
	 		}
	 		return null;
	 	}
	 	
	 	// VAlIDASI PUT (valIdNotNull,valIdExist, valBkNotNull, valBkNotChange, valNonBk)
	 	
	 	private static Exception valIdNotNull(Profile profile) throws Exception{
	 		if(profile.getProfileId() == null || profile.getProfileId().trim().equals("")) {
	 			throw new Exception("Id tidak boleh kosong");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valIdExist(String id) throws Exception{
	 		if(id == null) {
	 			throw new Exception("Profile tidak ditemukan!");
	 		}
	 		return null;
	 	}
	 	
//	 	private static Exception valBkNotChange(Profile oldProfile, Profile newProfile) throws Exception{
//	 		if( !oldProfile.getPhone().equalsIgnoreCase(newProfile.getPhone())
//	 				|| !oldProfile.getEmail().equalsIgnoreCase(newProfile.getEmail())) {
//	 			throw new Exception("UC tidak dapat diubah!");
//	 		}
//	 		return null;
//	 	}
	 	
	 	// VALIDASI DELETE ( valIdExist )
	
}
