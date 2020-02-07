package com.linov.psikotes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.PackageDao;
import com.linov.psikotes.dao.PackageQuestionDao;
import com.linov.psikotes.dao.QuestionDao;
import com.linov.psikotes.entity.PackageQuestion;
import com.linov.psikotes.entity.Question;
import com.linov.psikotes.pojo.PojoPackQuestion;
import com.linov.psikotes.pojo.PojoQuestion;
import com.linov.psikotes.pojo.PojoSearchPackageQuestion;

@Service("packageQuestionService")
public class PackageQuestionService {

	@Autowired
	private PackageQuestionDao pqDao;
	
	@Autowired
	private PackageDao pDao;
	
	@Autowired
	private QuestionDao qDao;

	public List<PackageQuestion> getAllPq(){
		List<PackageQuestion> list = pqDao.getAll();
		return list;
	}
		
	public PackageQuestion findById(String id) {
		PackageQuestion pq = pqDao.findById(id);
		return pq;
	}
	
	public List<PackageQuestion> search(PojoSearchPackageQuestion pq) {
		List<PackageQuestion> list = pqDao.search(pq);
		return list;
	}
	
	public List<PojoPackQuestion> findByPackageId(String id) {
		List<PackageQuestion> pq = pqDao.findByPackageId(id);
		
		List<PojoPackQuestion> list = new ArrayList<PojoPackQuestion>();
		
		for (PackageQuestion data : pq) {
			PojoPackQuestion ppq = new PojoPackQuestion();
			PojoQuestion pojoQ = new PojoQuestion();
			
			//set question without corect answer
			pojoQ.setActiveState(data.getQuestion().getActiveState());
			pojoQ.setChoice(data.getQuestion().getChoice());
			pojoQ.setListImg(data.getQuestion().getListImg());
			pojoQ.setQuestionDesc(data.getQuestion().getQuestionDesc());
			pojoQ.setQuestionId(data.getQuestion().getQuestionId());
			pojoQ.setQuestionTitle(data.getQuestion().getQuestionTitle());
			pojoQ.setQuestionType(data.getQuestion().getQuestionType());
			pojoQ.setTimestamp(data.getQuestion().getTimestamp());
			
			ppq.setPackageQuestionId(data.getPackageQuestionId());
			ppq.setQuestion(pojoQ);
			list.add(ppq);
		}
		return list;
	}
	
	public PackageQuestion insertPq(PackageQuestion pq) throws Exception{
		try {
			PackageQuestion pqobj = pqDao.findPackageQuestion(pq.getPack().getPackageId(), pq.getQuestion().getQuestionId());
			if (pqobj.getPackageQuestionId() != null) {
				pqobj.setActiveState("active");
				updatePq(pqobj);
				return pqobj;
			} else {
				//Check if id null
				valIdNull(pq);
				
				//Check if package id and question id not null
				valBkNotNull(pq);
				
				//Check if package question is not exist in DB
				PackageQuestion result = findPackageQuestion(pq);
				valBkNotExist(result);
				
				//Check if nonBK not null
				valNonBk(pq);
				
				//check if package as FK exist in DB
				com.linov.psikotes.entity.Package pack = pDao.findById(pq.getPack().getPackageId());
				valFkPackageNotNull(pack);
				
				//check if question as FK exist in DB
				Question question = qDao.findById(pq.getQuestion().getQuestionId());
				valFkQuestionNotNull(question);
				
				//save
				return pqDao.save(pq);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void updatePq(PackageQuestion pq) throws Exception{
		try {
			//Get old data of package question from DB
			PackageQuestion oldPq = findById(pq.getPackageQuestionId());
			
			//Check if id not null
			valIdNotNull(pq);
			
			//Check if id is exist in DB
			valIdExist(oldPq.getPackageQuestionId());
			
			//Check if package and question not null 
			valBkNotNull(pq);
			
			//Check if package and question not getting replaced
//			valBkNotChange(oldPq, pq);
			
			//Check if nonBK not null
			valNonBk(pq);
			
			//Save
			pqDao.save(pq);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void deletePq(String id) throws Exception{
		try {
			//Check if id is exist in DB
			valIdExist(id);
			//Delete
			pqDao.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public PackageQuestion findPackageQuestion(PackageQuestion pq) {
		return pqDao.findPackageQuestion(pq.getPack().getPackageId(), pq.getQuestion().getQuestionId());
	}
	
	// VALIDASI POST
	
	private static Exception valIdNull(PackageQuestion pq) throws Exception{
		if(pq.getPackageQuestionId() != null) {
			throw new Exception("Id harus kosong");
		}
		return null;
	}
	
	private static Exception valBkNotNull(PackageQuestion pq) throws Exception{
		if(pq.getPack().getPackageId() == null || pq.getPack().getPackageId().trim().equals("") 
				|| pq.getQuestion().getQuestionId() == null || pq.getQuestion().getQuestionId().trim().equals("") ) {
			throw new Exception("UC twidak boleh kosong");
		}
		return null;
	}
	
	private static Exception valBkNotExist(PackageQuestion pq) throws Exception{		
		if(pq.getPackageQuestionId()!=null) {
			throw new Exception("Pertanyaan sudah terdaftar didalam package");
		}
		return null;
	}
	
	private static Exception valNonBk(PackageQuestion pq) throws Exception{
		if( pq.getActiveState() == null || pq.getActiveState().trim().equals("")) {
			throw new Exception("Active state tidak boleh kosong");
		}
		return null;
	}
	
	private static Exception valFkPackageNotNull(com.linov.psikotes.entity.Package pack) throws Exception {
		if(pack.getPackageId() == null) {
			throw new Exception("Package tidak ditemukan");
		}
		return null;
	}
	
	private static Exception valFkQuestionNotNull(Question q) throws Exception {
		if(q.getQuestionId() == null) {
			throw new Exception("Question tidak ditemukan");
		}
		return null;
	}
	
	// VAlIDASI PUT (valIdNotNull,valIdExist, valBkNotNull, valBkNotChange, valNonBk)
	
	private static Exception valIdNotNull(PackageQuestion pq) throws Exception{
		if(pq.getPackageQuestionId() == null || pq.getPackageQuestionId().trim().equals("") ) {
			throw new Exception("Id tidak boleh kosong");
		}
		return null;
	}
	
	private static Exception valIdExist(String id) throws Exception{
		if(id == null) {
			throw new Exception("Package question belum dibuat!");
		}
		return null;
	}
	
//	private static Exception valBkNotChange(PackageQuestion oldPq, PackageQuestion newPq) throws Exception{
//		if(!oldPq.getPack().getPackageId().equalsIgnoreCase(newPq.getPack().getPackageId())
//				|| !oldPq.getQuestion().getQuestionId().equalsIgnoreCase(newPq.getQuestion().getQuestionId())) {
//			throw new Exception("UC tidak dapat diubah!");
//		}
//		return null;
//	}
	
	// VALIDASI DELETE ( valIdExist )
	
}
