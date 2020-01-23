package com.linov.psikotes.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.linov.psikotes.dao.DetailAppAnsDao;
import com.linov.psikotes.entity.DetailApplicantAnswer;
import com.linov.psikotes.pojo.PojoReportCandidate;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service("reportService")
public class ReportService {
	
	@Autowired
	private DetailAppAnsDao detailAppAnsDao;
	
	public String candidateReport(String reportFormat, String id) throws FileNotFoundException, JRException{
		String path = "D:\\";
		
		List<DetailApplicantAnswer> listDetail = detailAppAnsDao.getAllByAppAnsId(id);
		
		//set to pojo
		List<PojoReportCandidate> listReport = new ArrayList<PojoReportCandidate>();
		for (DetailApplicantAnswer data : listDetail) {
			PojoReportCandidate prc = new PojoReportCandidate();
			prc.setProfileName(data.getHeaderAppAnswer().getUser().getProfile().getProfileName());
			prc.setAddress(data.getHeaderAppAnswer().getUser().getProfile().getAddress());
			prc.setPhone(data.getHeaderAppAnswer().getUser().getProfile().getPhone());
			prc.setEmail(data.getHeaderAppAnswer().getUser().getProfile().getEmail());
			prc.setQuestionTitle(data.getPackQuestion().getQuestion().getQuestionTitle());
			prc.setQuestionTypeTitle(data.getPackQuestion().getQuestion().getQuestionType().getQuestionTypeTitle());
			prc.setPoint(data.getPoint());
			prc.setTotalPoints(data.getHeaderAppAnswer().getTotalPoints());
			listReport.add(prc);
		}
		
		//Load file and compile it
		File file = ResourceUtils.getFile("classpath:report/candidate.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listReport);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put("CreatedBy", "Lawencon International");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);
		
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\Candidate.html");
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\Candidate.pdf");
		}
		
		return "Report Generated in path : " + path;
	}
	
}
