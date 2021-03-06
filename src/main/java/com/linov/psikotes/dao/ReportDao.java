package com.linov.psikotes.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.User;
import com.linov.psikotes.pojo.PojoPackReport;
import com.linov.psikotes.pojo.PojoPackage;
import com.linov.psikotes.pojo.PojoQuestReport;

@Repository("reportDao")
public class ReportDao extends CommonDao{
	
	@Autowired
	private PackageDao packDao;
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PojoQuestReport> getTheMostTrueAnsQuest() {
		Query query  = super.entityManager
				.createNativeQuery("select tmq.question_title\r\n" + 
						"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
						"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
						"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
						"where tdaa.point <> 0 \r\n" + 
						"group by tmq.question_title\r\n" + 
						"order by count(tmq.question_title) desc limit 10");

		List<String> listQuestTitle = query.getResultList(); 
		
			  query  = super.entityManager
				.createNativeQuery("select count(tmq.question_title)\r\n" + 
						"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
						"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
						"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
						"where tdaa.point <> 0\r\n" + 
						"group by tmq.question_title\r\n" + 
						"order by count(tmq.question_title) desc limit 10");

		List<BigInteger> listTotalCorrect = query.getResultList();
		
		  query  = super.entityManager
					.createNativeQuery("select count(tmq.question_title) \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
							"group by tmq.question_title\r\n" + 
							"order by count(tmq.question_title) desc limit 10");

			List<BigInteger> listTotalQuest = query.getResultList();
			
		List<User> list = super.entityManager
				.createQuery("from User where role_id = 'role2' ")
				.getResultList();
		
		Integer totalCandidate = list.size();

		//Make List Pojo Question Report
		List<PojoQuestReport> listPqr = new ArrayList<PojoQuestReport>();
		
		
		for (int i = 0; i < listQuestTitle.size(); i++) {
			PojoQuestReport pqr = new PojoQuestReport();
			
			//Buat persen dari total soal dengan total yang dijawab benar
			Double percent =  (listTotalCorrect.get(i).doubleValue()/listTotalQuest.get(i).doubleValue())*100; 
			String format = String.format("%.2f", percent);
			percent = Double.parseDouble(format);
			
			pqr.setQuestionTitle(listQuestTitle.get(i));
			pqr.setTotalCorrect(listTotalCorrect.get(i).toString());
			pqr.setTotalQuestion(listTotalQuest.get(i).toString());
			pqr.setPercentation(percent.toString());
			pqr.setTotalCandidate(totalCandidate.toString());
			listPqr.add(pqr);
		}
		
		//sorting by percentage using bubble sort
		
		for (int i = 0; i < listPqr.size()-1; i++) {
			for (int j = 0; j < listPqr.size()-i-1; j++) {
				if(Double.parseDouble(listPqr.get(j).getPercentation()) < Double.parseDouble(listPqr.get(j+1).getPercentation())) {
					PojoQuestReport temp = listPqr.get(j);
					listPqr.set(j, listPqr.get(j+1)); 
					listPqr.set(j+1, temp);
				}
			}
		}
		
		return listPqr;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PojoQuestReport> getTheMostFalseAnsQuest() {
		Query query  = super.entityManager
				.createNativeQuery("select tmq.question_title\r\n" + 
						"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
						"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
						"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
						"where tdaa.point = 0\r\n" + 
						"group by tmq.question_title\r\n" + 
						"order by count(tmq.question_title) desc\r\n" + 
						"limit 10");

		List<String> listQuestTitle = query.getResultList(); 
		
			  query  = super.entityManager
				.createNativeQuery("select count(tmq.question_title)\r\n" + 
						"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
						"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
						"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
						"where tdaa.point = 0\r\n" + 
						"group by tmq.question_title\r\n" + 
						"order by count(tmq.question_title) desc\r\n" + 
						"limit 10");

		List<BigInteger> listTotalCorrect = query.getResultList();
		
		 query  = super.entityManager
					.createNativeQuery("select count(tmq.question_title) as total\r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
							"group by tmq.question_title\r\n" + 
							"order by total desc");

			List<BigInteger> listTotalQuest = query.getResultList();
			
		List<User> list = super.entityManager
				.createQuery("from User where role_id = 'role2' ")
				.getResultList();
		
		Integer totalCandidate = list.size();
	
		//Make List Pojo Question Report
		List<PojoQuestReport> listPqr = new ArrayList<PojoQuestReport>();
		
		
		for (int i = 0; i < listQuestTitle.size(); i++) {
			PojoQuestReport pqr = new PojoQuestReport();
			
			//Buat persen dari total soal dengan total yang dijawab benar
			Double percent =  listTotalCorrect.get(i).doubleValue()/listTotalQuest.get(i).doubleValue()*100;
			String format = String.format("%.2f", percent);
			percent = Double.parseDouble(format);
			
			pqr.setQuestionTitle(listQuestTitle.get(i));
			pqr.setTotalCorrect(listTotalCorrect.get(i).toString());
			pqr.setTotalQuestion(listTotalQuest.get(i).toString());
			pqr.setPercentation(percent.toString());
			pqr.setTotalCandidate(totalCandidate.toString());
			listPqr.add(pqr);
		}
		
		//sorting by percentage using bubble sort
		
		for (int i = 0; i < listPqr.size()-1; i++) {
			for (int j = 0; j < listPqr.size()-i-1; j++) {
				if(Double.parseDouble(listPqr.get(j).getPercentation()) < Double.parseDouble(listPqr.get(j+1).getPercentation())) {
					PojoQuestReport temp = listPqr.get(j);
					listPqr.set(j, listPqr.get(j+1)); 
					listPqr.set(j+1, temp);
				}
			}
		}
		
		return listPqr;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PojoPackReport> getTheMostCorrectAnsPack() {
		
		//Get all package that available
		List<PojoPackage> listPack = packDao.getAll();
		
		//Make list PojoPackReport
		List<PojoPackReport> listPackReport = new ArrayList<PojoPackReport>();
		
		for (int i = 0; i < listPack.size(); i++) {
			Query query  = super.entityManager
					.createNativeQuery("select tbp.package_name \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa \r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id \r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id \r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id \r\n" + 
							"where tdaa.point <> 0 and lower(tbp.package_name) = '"+ listPack.get(i).getPackageName().toLowerCase() +"' and lower(tbp.active_state) = 'active' \r\n" + 
							"group by tbp.package_name,tmq.question_title \r\n" + 
							"order by count(tmq.question_title) desc \r\n" + 
							"limit 10");

			List<String> listPackName = query.getResultList(); 
			
				  query  = super.entityManager
					.createNativeQuery("select tmq.question_title \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id\r\n" + 
							"where tdaa.point <> 0 and lower(tbp.package_name) = '"+ listPack.get(i).getPackageName().toLowerCase() +"' and lower(tbp.active_state) = 'active' \r\n" + 
							"group by tbp.package_name,tmq.question_title\r\n" + 
							"order by count(tmq.question_title) desc\r\n" + 
							"limit 10");

			List<String> listQuestTitle = query.getResultList(); 
			
				  query  = super.entityManager
					.createNativeQuery("select count(tmq.question_title) \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id\r\n" + 
							"where tdaa.point <> 0 and lower(tbp.package_name) = '"+ listPack.get(i).getPackageName().toLowerCase() +"' and lower(tbp.active_state) = 'active' \r\n" + 
							"group by tbp.package_name,tmq.question_title\r\n" + 
							"order by count(tmq.question_title) desc\r\n" + 
							"limit 10");

			List<BigInteger> listTotalCorrect = query.getResultList();
			
			 query  = super.entityManager
					.createNativeQuery("select count(tmq.question_title)\r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id\r\n" + 
							"where lower(tbp.package_name) ='" + listPack.get(i).getPackageName().toLowerCase() + "'\r\n" + 
							"group by tmq.question_title, tbp.package_name\r\n" + 
							"order by count(tmq.question_title) desc limit 10");

				List<BigInteger> listTotalQuestion = query.getResultList();
			
			List<User> list = super.entityManager
					.createQuery("from User where role_id = 'role2' ")
					.getResultList();
			
			Integer totalCandidate = list.size();
				
			for (int j = 0; j < listPackName.size(); j++) {
				
				PojoPackReport ppr = new PojoPackReport();
				
				//Get percentation
				Double percentation = listTotalCorrect.get(j).doubleValue()/listTotalQuestion.get(j).doubleValue()*100;
				String format = String.format("%.2f", percentation);
				percentation = Double.parseDouble(format);
				
				ppr.setPackageName(listPackName.get(j));
				ppr.setQuestionTitle(listQuestTitle.get(j));
				ppr.setTotalQuestion(listTotalQuestion.get(j).toString());
				ppr.setTotalCorrect(listTotalCorrect.get(j).toString());
				ppr.setPercentation(percentation.toString());
				ppr.setTotalCandidate(totalCandidate.toString());
				listPackReport.add(ppr);
			}
		} // end for package
		
		//sorting by percentage using bubble sort
		
		for (int i = 0; i < listPackReport.size()-1; i++) {
			for (int j = 0; j < listPackReport.size()-i-1; j++) {
				if(Double.parseDouble(listPackReport.get(j).getPercentation()) < Double.parseDouble(listPackReport.get(j+1).getPercentation())) {
					PojoPackReport temp = listPackReport.get(j);
					listPackReport.set(j, listPackReport.get(j+1)); 
					listPackReport.set(j+1, temp);
				}
			}
		}
		
		return listPackReport;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PojoPackReport> getTheMostWrongAnsPack() {
		
		//Get all package that available
		List<PojoPackage> listPack = packDao.getAll();
		
		//Make list PojoPackReport
		List<PojoPackReport> listPackReport = new ArrayList<PojoPackReport>();
		
		for (int i = 0; i < listPack.size(); i++) {
			Query query  = super.entityManager
					.createNativeQuery("select tbp.package_name \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa \r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id \r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id \r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id \r\n" + 
							"where tdaa.point = 0 and lower(tbp.package_name) = '"+ listPack.get(i).getPackageName().toLowerCase() +"' and lower(tbp.active_state) = 'active' \r\n" + 
							"group by tbp.package_name,tmq.question_title \r\n" + 
							"order by count(tmq.question_title) desc \r\n" + 
							"limit 10");

			List<String> listPackName = query.getResultList(); 
			
				  query  = super.entityManager
					.createNativeQuery("select tmq.question_title \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id\r\n" + 
							"where tdaa.point = 0 and lower(tbp.package_name) = '"+ listPack.get(i).getPackageName().toLowerCase() +"' and lower(tbp.active_state) = 'active' \r\n" + 
							"group by tbp.package_name,tmq.question_title\r\n" + 
							"order by count(tmq.question_title) desc\r\n" + 
							"limit 10");

			List<String> listQuestTitle = query.getResultList(); 
			
				  query  = super.entityManager
					.createNativeQuery("select count(tmq.question_title) \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id\r\n" + 
							"where tdaa.point = 0 and lower(tbp.package_name) = '"+ listPack.get(i).getPackageName().toLowerCase() +"' and lower(tbp.active_state) = 'active' \r\n" + 
							"group by tbp.package_name,tmq.question_title\r\n" + 
							"order by count(tmq.question_title) desc\r\n" + 
							"limit 10");

			List<BigInteger> listTotalCorrect = query.getResultList();
			
			 query  = super.entityManager
				.createNativeQuery("select count(tmq.question_title)\r\n" + 
						"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
						"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
						"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
						"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id\r\n" + 
						"where lower(tbp.package_name) ='" + listPack.get(i).getPackageName().toLowerCase() + "'\r\n" + 
						"group by tmq.question_title, tbp.package_name\r\n" + 
						"order by count(tmq.question_title) desc limit 10");

					List<BigInteger> listTotalQuestion = query.getResultList();
			
			List<User> list = super.entityManager
					.createQuery("from User where role_id = 'role2' ")
					.getResultList();
			
			Integer totalCandidate = list.size();
				
			for (int j = 0; j < listPackName.size(); j++) {
				PojoPackReport ppr = new PojoPackReport();
				
				//Get percentation
				Double percentation = listTotalCorrect.get(j).doubleValue()/listTotalQuestion.get(j).doubleValue()*100;
				String format = String.format("%.2f", percentation);
				percentation = Double.parseDouble(format);
				
				ppr.setPackageName(listPackName.get(j));
				ppr.setQuestionTitle(listQuestTitle.get(j));
				ppr.setTotalQuestion(listTotalQuestion.get(j).toString());
				ppr.setTotalCorrect(listTotalCorrect.get(j).toString());
				ppr.setPercentation(percentation.toString());
				ppr.setTotalCandidate(totalCandidate.toString());
				listPackReport.add(ppr);
			}
		} // end for package
		
		//sorting by percentage using bubble sort
		
		for (int i = 0; i < listPackReport.size()-1; i++) {
			for (int j = 0; j < listPackReport.size()-i-1; j++) {
				if(Double.parseDouble(listPackReport.get(j).getPercentation()) < Double.parseDouble(listPackReport.get(j+1).getPercentation())) {
					PojoPackReport temp = listPackReport.get(j);
					listPackReport.set(j, listPackReport.get(j+1)); 
					listPackReport.set(j+1, temp);
				}
			}
		}
		
		return listPackReport;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PojoPackReport> getPackageByTheMostCorrectAnswer() {
		
		//Get all package that available
		List<PojoPackage> listPack = packDao.getAll();
		
		//Make list PojoPackReport
		List<PojoPackReport> listPackReport = new ArrayList<PojoPackReport>();
		
		for (int i = 0; i < listPack.size(); i++) {
			Query query  = super.entityManager
					.createNativeQuery("select tbp.package_name \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa \r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id \r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id \r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id \r\n" + 
							"where tdaa.point <> 0 and lower(tbp.active_state) = 'active'and lower(tbp.package_name) ='"+ listPack.get(i).getPackageName().toLowerCase() +"' \r\n" + 
							"group by tbp.package_name \r\n" + 
							"order by count(tmq.question_title) desc \r\n" + 
							"limit 10");

			List<String> listPackName = query.getResultList(); 
						
				  query  = super.entityManager
					.createNativeQuery("select count(tbp.package_name) \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id\r\n" + 
							"where tdaa.point <> 0 and lower(tbp.active_state) = 'active' and lower(tbp.package_name) ='"+ listPack.get(i).getPackageName().toLowerCase() +"' \r\n" + 
							"group by tbp.package_name\r\n" + 
							"order by count(tmq.question_title) desc\r\n" + 
							"limit 10");

			List<BigInteger> listTotalCorrect = query.getResultList();
			
				query  = super.entityManager
						.createNativeQuery("select count(tmq.question_title)\r\n" + 
								"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
								"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
								"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
								"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id\r\n" + 
								"where lower(tbp.package_name) ='"+ listPack.get(i).getPackageName().toLowerCase() +"' and lower(tbp.active_state) = 'active' \r\n" + 
								"group by tbp.package_name\r\n" + 
								"order by count(tmq.question_title) desc");

			List<BigInteger> listTotalQuestion = query.getResultList();
			
			List<User> list = super.entityManager
					.createQuery("from User where role_id = 'role2' ")
					.getResultList();
			
			Integer totalCandidate = list.size();
		
			for (int j = 0; j < listPackName.size(); j++) {
				PojoPackReport ppr = new PojoPackReport();
				
				//Get percentation
				Double percentation = listTotalCorrect.get(j).doubleValue()/listTotalQuestion.get(j).doubleValue()*100;
				String format = String.format("%.2f", percentation);
				percentation = Double.parseDouble(format);
				
				ppr.setPackageName(listPackName.get(j));
				ppr.setTotalCorrect(listTotalCorrect.get(j).toString());
				ppr.setTotalQuestion(listTotalQuestion.get(j).toString());
				ppr.setPercentation(percentation.toString());
				ppr.setTotalCandidate(totalCandidate.toString());
				listPackReport.add(ppr);
			}
		
		}//end package
		
		//sorting by percentage using bubble sort
		
		for (int i = 0; i < listPackReport.size()-1; i++) {
			for (int j = 0; j < listPackReport.size()-i-1; j++) {
				if(Double.parseDouble(listPackReport.get(j).getPercentation()) < Double.parseDouble(listPackReport.get(j+1).getPercentation())) {
					PojoPackReport temp = listPackReport.get(j);
					listPackReport.set(j, listPackReport.get(j+1)); 
					listPackReport.set(j+1, temp);
				}
			}
		}
		
		return listPackReport;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PojoPackReport> getPackageByTheMostWrongAnswer() {
			
		//Get all package that available
		List<PojoPackage> listPack = packDao.getAll();
		
		//Make list PojoPackReport
		List<PojoPackReport> listPackReport = new ArrayList<PojoPackReport>();
		
		for (int i = 0; i < listPack.size(); i++) {
			Query query  = super.entityManager
					.createNativeQuery("select tbp.package_name \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa \r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id \r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id \r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id \r\n" + 
							"where tdaa.point = 0 and lower(tbp.active_state) = 'active'and lower(tbp.package_name) ='"+ listPack.get(i).getPackageName().toLowerCase() +"' \r\n" + 
							"group by tbp.package_name \r\n" + 
							"order by count(tmq.question_title) desc \r\n" + 
							"limit 10");

			List<String> listPackName = query.getResultList(); 
						
				  query  = super.entityManager
					.createNativeQuery("select count(tbp.package_name) \r\n" + 
							"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
							"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
							"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
							"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id\r\n" + 
							"where tdaa.point = 0 and lower(tbp.active_state) = 'active' and lower(tbp.package_name) ='"+ listPack.get(i).getPackageName().toLowerCase() +"' \r\n" + 
							"group by tbp.package_name\r\n" + 
							"order by count(tmq.question_title) desc\r\n" + 
							"limit 10");

			List<BigInteger> listTotalCorrect = query.getResultList();
			
				query  = super.entityManager
						.createNativeQuery("select count(tmq.question_title)\r\n" + 
								"from group1.tbl_detail_applicant_answer tdaa\r\n" + 
								"join group1.tbl_package_question tpq on tdaa.package_question_id = tpq.package_question_id\r\n" + 
								"join group1.tbl_m_question tmq on tpq.question_id = tmq.question_id\r\n" + 
								"join group1.tbl_m_package tbp on tpq.package_id = tbp.package_id\r\n" + 
								"where lower(tbp.package_name) ='"+ listPack.get(i).getPackageName().toLowerCase() +"' and lower(tbp.active_state) = 'active' \r\n" + 
								"group by tbp.package_name\r\n" + 
								"order by count(tmq.question_title) desc");

			List<BigInteger> listTotalQuestion = query.getResultList();
			
			List<User> list = super.entityManager
					.createQuery("from User where role_id = 'role2' ")
					.getResultList();
			
			Integer totalCandidate = list.size();
		
			for (int j = 0; j < listPackName.size(); j++) {
				PojoPackReport ppr = new PojoPackReport();
				
				//Get percentation
				Double percentation = listTotalCorrect.get(j).doubleValue()/listTotalQuestion.get(j).doubleValue()*100;
				String format = String.format("%.2f", percentation);
				percentation = Double.parseDouble(format);
				
				ppr.setPackageName(listPackName.get(j));
				ppr.setTotalCorrect(listTotalCorrect.get(j).toString());
				ppr.setTotalQuestion(listTotalQuestion.get(j).toString());
				ppr.setPercentation(percentation.toString());
				ppr.setTotalCandidate(totalCandidate.toString());
				listPackReport.add(ppr);
			}
		
		}//end package
		
		//sorting by percentage using bubble sort
		
		for (int i = 0; i < listPackReport.size()-1; i++) {
			for (int j = 0; j < listPackReport.size()-i-1; j++) {
				if(Double.parseDouble(listPackReport.get(j).getPercentation()) < Double.parseDouble(listPackReport.get(j+1).getPercentation())) {
					PojoPackReport temp = listPackReport.get(j);
					listPackReport.set(j, listPackReport.get(j+1)); 
					listPackReport.set(j+1, temp);
				}
			}
		}
		
		return listPackReport;
	}
}
