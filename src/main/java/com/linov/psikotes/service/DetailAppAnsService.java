package com.linov.psikotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.DetailAppAnsDao;
import com.linov.psikotes.dao.HeaderAppAnsDao;
import com.linov.psikotes.entity.DetailApplicantAnswer;
import com.linov.psikotes.entity.HeaderApplicantAnswer;
import com.linov.psikotes.entity.PackageQuestion;

@Service("detailAppAnsService")
public class DetailAppAnsService {
	
	@Autowired
	private DetailAppAnsDao dAppAnsDao;
	
	@Autowired
	private HeaderAppAnsDao appAnsDao;
	
	@Autowired
	private PackageQuestionService pqDao;

	public List<DetailApplicantAnswer> getAllDetail(){
		List<DetailApplicantAnswer> list = dAppAnsDao.getAll();
		return list;
	}
	
	public DetailApplicantAnswer findById(String id) {
		DetailApplicantAnswer dAppAns = dAppAnsDao.findById(id);
		return dAppAns;
	}
	
	public DetailApplicantAnswer findBK(DetailApplicantAnswer detailAns) {
		DetailApplicantAnswer result = dAppAnsDao.findBK(detailAns.getHeaderAppAnswer().getApplicantAnswerId(), 
				detailAns.getPackQuestion().getPackageQuestionId());
		return result;
	}
	
	public DetailApplicantAnswer insertDetail(DetailApplicantAnswer detailAns) throws Exception{
		try {
			//Check if id null 
			valIdNull(detailAns);
			
			//Check if user null or not
			valBkNotNull(detailAns);
			
			//Check if applicant answer with package question not exist in DB 
			valBkNotExist(findBK(detailAns));
			
			//Check if header as FK is exist in DB
			HeaderApplicantAnswer appAns = appAnsDao.findById(detailAns.getHeaderAppAnswer().getApplicantAnswerId());
			valFkAnswerId(appAns);
			
			//Check if packQuest as FK is exist in DB
			PackageQuestion packQuest = pqDao.findById(detailAns.getPackQuestion().getPackageQuestionId());
			valFkPackQuestId(packQuest);
			
			//Check if nonBK null or not
			valNonBk(detailAns);
			
			//Save
			return dAppAnsDao.save(detailAns);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateProfile(DetailApplicantAnswer detailAns) throws Exception{
		try {
			//Get old data of detail Answer
			DetailApplicantAnswer oldDetailAns= findById(detailAns.getDetailAnswerId());
			
			//Check if appAnsId null or not
			valIdNotNull(detailAns);
			
			//check if id already exist in DB
			valIdExist(oldDetailAns.getDetailAnswerId());
			
			//Check if applicant answer id and  package question not null
			valBkNotNull(detailAns);
			
			//Check if applicant answer id and package question not getting replaced
			valBkNotChange(oldDetailAns, detailAns);
			
			//Check if nonBk null or not
			valNonBk(detailAns);
			
			//save
			dAppAnsDao.save(detailAns);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteProfile(String id) throws Exception{
		try {
			//Check if id exist in DB
			valIdExist(id);
			
			//delete
			dAppAnsDao.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	// VALIDASI POST
	
	private static Exception valIdNull(DetailApplicantAnswer detailAns) throws Exception{
		if(detailAns.getDetailAnswerId() != null) {
			throw new Exception("Id detail harus kosong");
		}
		return null;
	}
	
	private static Exception valBkNotNull(DetailApplicantAnswer detailAns) throws Exception{
		if(detailAns.getHeaderAppAnswer().getApplicantAnswerId() == null || detailAns.getHeaderAppAnswer().getApplicantAnswerId().trim().equals("") 
				|| detailAns.getPackQuestion().getPackageQuestionId() == null || detailAns.getPackQuestion().getPackageQuestionId().trim().equals("")) {
			throw new Exception("User dan question tidak boleh kosong");
		}
		return null;
	}
	
	private static Exception valBkNotExist(DetailApplicantAnswer detailAns) throws Exception{		
		if(detailAns.getDetailAnswerId() != null) {
			throw new Exception("User sudah menjawab pertanyaan tersebut");
		}
		return null;
	}
	
	private static Exception valNonBk(DetailApplicantAnswer detailAns) throws Exception{
		if( detailAns.getAppAnswer().getAnswer1() == null || detailAns.getAppAnswer().getAnswer2() == null
				|| detailAns.getPoint() == null) {
			throw new Exception("Tidak boleh ada field yang kosong");
		}
		return null;
	}
	
	private static Exception valFkAnswerId(HeaderApplicantAnswer appAns) throws Exception{
		if(appAns.getApplicantAnswerId() == null) {
			throw new Exception("header tidak ditemukan!");
		}
		return null;
	}
	
	private static Exception valFkPackQuestId(PackageQuestion pq) throws Exception{
		if(pq.getPackageQuestionId() == null) {
			throw new Exception("Package question tidak ditemukan!");
		}
		return null;
	}
	
	// VAlIDASI PUT (valIdNotNull,valIdExist, valBkNotNull, valBkNotChange, valNonBk)
	
	private static Exception valIdNotNull(DetailApplicantAnswer detailAns) throws Exception{
		if(detailAns.getDetailAnswerId() == null || detailAns.getDetailAnswerId().trim().equals("")) {
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
	
	private static Exception valBkNotChange(DetailApplicantAnswer oldDetailAns, DetailApplicantAnswer newDetailAns) throws Exception{
		if(!oldDetailAns.getHeaderAppAnswer().getApplicantAnswerId().equalsIgnoreCase(newDetailAns.getHeaderAppAnswer().getApplicantAnswerId())
				|| !oldDetailAns.getPackQuestion().getPackageQuestionId().equalsIgnoreCase(newDetailAns.getPackQuestion().getPackageQuestionId()) ) {
			throw new Exception("UC tidak dapat diubah!");
		}
		return null;
	}
	
	// VALIDASI DELETE ( valIdExist )
	
}
