package com.linov.psikotes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.entity.Package;
import com.linov.psikotes.dao.AssignQuestionDao;
import com.linov.psikotes.entity.AssignQuestion;
import com.linov.psikotes.entity.PackageQuestion;
import com.linov.psikotes.pojo.PojoSearchAssignQuest;

@Service("assignQuestionService")
public class AssignQuestionService {

	@Autowired
	private AssignQuestionDao aqDao;

	public List<AssignQuestion> getAllAq(){
		List<AssignQuestion> list = aqDao.getAll();
		return list;
	}
	
	public List<PackageQuestion> getAllQuestByUserId(String userId){
		return aqDao.getAllQuestByUserId(userId);
	}
	
	public List<AssignQuestion> search(PojoSearchAssignQuest aq){
		return aqDao.search(aq);
	}
	
	public List<Package> getAllPackageByUserId(String userId){
		List<AssignQuestion> list =  aqDao.getAllPackageByUserId(userId);
		List<Package> listPack = new ArrayList<Package>();
		
		for (AssignQuestion aq : list) {
			Package p = new Package();
			p.setPackageId(aq.getPack().getPackageId());
			p.setPackageName(aq.getPack().getPackageName());
			p.setAmountOfTime(aq.getPack().getAmountOfTime());
			p.setActiveState(aq.getPack().getActiveState());
			listPack.add(p);
		}
		return listPack;
	}
	
	public AssignQuestion findById(String id) {
		AssignQuestion aq = aqDao.findById(id);
		return aq;
	}
	
	public AssignQuestion insertAq(AssignQuestion aq) throws Exception{
		try {
			AssignQuestion newAq = aqDao.findBK(aq.getUser().getUserId(), aq.getPack().getPackageId());
			if(newAq.getAssignQuestionId() != null && newAq.getActiveState().equalsIgnoreCase("inactive")) {
				newAq.setActiveState("active");
				updateAq(newAq);
				return newAq;
			} else {
				//Check if id is null
				valIdNull(aq);
				
				//Check if user id and package question id not null
				valBkNotNull(aq);
				
				//Check if user id and package question are not exist in DB
				AssignQuestion resultAq = findBK(aq);
				valBkNotExist(resultAq);
				
				//Check if nonBK not null
				valNonBk(aq);
				
				//Save
				return aqDao.save(aq);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateAq(AssignQuestion aq) throws Exception{
		try {
			//Get old data of assign question from DB
			AssignQuestion oldAq = findById(aq.getAssignQuestionId());
			
			//Check if id not null
			valIdNotNull(aq);
			
			//Check if user and package question id not null
			valBkNotNull(aq);
			
			//Check if user and package question not getting replaced
			valBkNotChange(oldAq, aq);
			
			//Check if nonBk not null
			valNonBk(aq);
			
			//Save
			aqDao.save(aq);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
		
	}
	
	public void deleteAq(String id) throws Exception{
		try {
			//Check if id is exist in DB
			valIdExist(id);
			
			//Delete
			aqDao.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public AssignQuestion findBK(AssignQuestion aq) {
		return aqDao.findBK(aq.getUser().getUserId(), aq.getPack().getPackageId());
	}
	
	// VALIDASI POST
	
	 	private static Exception valIdNull(AssignQuestion aq) throws Exception{
	 		if(aq.getAssignQuestionId() !=null) {
	 			throw new Exception("Id harus kosong");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valBkNotNull(AssignQuestion aq) throws Exception{
	 		if (aq.getUser().getUserId() == null || aq.getUser().getUserId().trim().equals("") 
	 				|| aq.getPack().getPackageId() == null || aq.getPack().getPackageId().trim().equals("")) 
	 		{
	 			throw new Exception("User dan package tidak boleh kosong");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valBkNotExist(AssignQuestion aq) throws Exception{		
	 		if(aq.getAssignQuestionId() != null) {
	 			throw new Exception("Question sudah di assign ke user");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valNonBk(AssignQuestion aq) throws Exception{
	 		if( aq.getActiveState() == null || aq.getActiveState().trim().equals("")) 
	 		{
	 			throw new Exception("Active state tidak boleh kosong");
	 		}
	 		return null;
	 	}
	 	
	 	// VAlIDASI PUT (valIdNotNull,valIdExist, valBkNotNull, valBkNotChange, valNonBk)
	 	
	 	private static Exception valIdNotNull(AssignQuestion aq) throws Exception{
	 		if(aq.getAssignQuestionId() == null || aq.getAssignQuestionId().trim().equals("")) {
	 			throw new Exception("Id tidak boleh kosong");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valIdExist(String id) throws Exception{
	 		if(id == null) {
	 			throw new Exception("Assign Question tidak ditemukan!");
	 		}
	 		return null;
	 	}
	 	
	 	private static Exception valBkNotChange(AssignQuestion oldAq, AssignQuestion newAq) throws Exception{
	 		if( !oldAq.getUser().getUserId().equalsIgnoreCase(newAq.getUser().getUserId())
	 				|| !oldAq.getPack().getPackageId().equalsIgnoreCase(newAq.getPack().getPackageId())) {
	 			throw new Exception("UC tidak dapat diubah!");
	 		}
	 		return null;
	 	}
	 	
	 	// VALIDASI DELETE ( valIdExist )
	
}
