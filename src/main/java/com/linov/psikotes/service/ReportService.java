package com.linov.psikotes.service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.linov.psikotes.dao.DetailAppAnsDao;
import com.linov.psikotes.dao.ReportDao;
import com.linov.psikotes.entity.DetailApplicantAnswer;
import com.linov.psikotes.exception.MyFileNotFoundException;
import com.linov.psikotes.pojo.PojoPackReport;
import com.linov.psikotes.pojo.PojoQuestReport;
import com.linov.psikotes.pojo.PojoReportCandidate;

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
	
	@Autowired
	private ReportDao reportDao;
	
	@Value("${report.folder}")
	private Path fileStorageLocation;
	
	 public Resource loadFileAsResource(String fileName) {
	        try {
	            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
	            Resource resource = new UrlResource(filePath.toUri());
	            if(resource.exists()) {
	                return resource;
	            } else {
	                throw new MyFileNotFoundException("File not found " + fileName);
	            }
	        } catch (MalformedURLException ex) {
	            throw new MyFileNotFoundException("File not found " + fileName, ex);
	        }
    }
	
	public String candidateReport(String reportFormat, String id, HttpServletRequest request) throws Exception{
		
		String fileName = "";
		
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
		
		//create directory
//		Path p = Paths.get(fileStorageLocation.toString());
//		if(!Files.exists(p)) {
//			try {
//				Files.createDirectories(p);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
		//Load file and compile it
		File file = ResourceUtils.getFile("classpath:report/candidate.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listReport);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put("CreatedBy", "Lawencon International");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);
		
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,fileStorageLocation.toString()+File.separator+"Candidate.html");
			fileName = "Candidate.html";
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,fileStorageLocation.toString()+File.separator+"Candidate.pdf");
			fileName = "Candidate.pdf";
		}
		
		return fileName;
	}
	
	public String reportCorrectQuestion(String reportFormat) throws Exception{
		
		String fileName = "";
		
		//Get the list from dao
		List<PojoQuestReport> list = reportDao.getTheMostTrueAnsQuest();
		
		//Load file and compile it
		File file = ResourceUtils.getFile("classpath:report/MostCorrectAnswer.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put("CreatedBy", "Lawencon International");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);
		
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,fileStorageLocation+File.separator+"MostCorrectAnswer.html");
			fileName = "MostCorrectAnswer.html";
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,fileStorageLocation+File.separator+"MostCorrectAnswer.pdf");
			fileName = "MostCorrectAnswer.pdf";
		}
		
		return fileName;
		
	}
	
	public String reportFalseQuestion(String reportFormat) throws Exception{
		
		String fileName = "";
		
		//Get the list from dao
		List<PojoQuestReport> list = reportDao.getTheMostFalseAnsQuest();
		
		//Load file and compile it
		File file = ResourceUtils.getFile("classpath:report/MostWrongAnswer.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put("CreatedBy", "Lawencon International");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);
		
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,fileStorageLocation+File.separator+"MostWrongAnswer.html");
			fileName = "MostWrongAnswer.html";
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,fileStorageLocation+File.separator+"MostWrongAnswer.pdf");
			fileName = "MostWrongAnswer.pdf";
		}
		
		return fileName;
		
	}
	
	public String reportCorrectAnsAtPack(String reportFormat) throws Exception{
		
		String fileName = "";
		
		//Get the list from dao
		List<PojoPackReport> list = reportDao.getTheMostCorrectAnsPack();
		
		//Load file and compile it
		File file = ResourceUtils.getFile("classpath:report/MostCorrectAnsBaseOnPack.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put("CreatedBy", "Lawencon International");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);
		
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,fileStorageLocation+File.separator+"MostCorrectAnsBaseOnPack.html");
			fileName = "MostCorrectAnsBaseOnPack.html";
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,fileStorageLocation+File.separator+"MostCorrectAnsBaseOnPack.pdf");
			fileName = "MostCorrectAnsBaseOnPack.pdf";
		}
		
		return fileName;

	}
	
	public String reportWrongAnsAtPack(String reportFormat) throws Exception{
		
		String fileName = "";
		
		//Get the list from dao
		List<PojoPackReport> list = reportDao.getTheMostWrongAnsPack();
		
		//Load file and compile it
		File file = ResourceUtils.getFile("classpath:report/MostWrongAnsBaseOnPack.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put("CreatedBy", "Lawencon International");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);
		
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,fileStorageLocation+File.separator+"MostWrongAnsBaseOnPack.html");
			fileName = "MostWrongAnsBaseOnPack.html";
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,fileStorageLocation+File.separator+"MostWrongAnsBaseOnPack.pdf");
			fileName = "MostWrongAnsBaseOnPack.pdf";
		}
		
		return fileName;

	}
	
	public String reportGetPackageByTheMostCorrectAnswer(String reportFormat) throws Exception{
		
		String fileName = "";
		
		//Get the list from dao
		List<PojoPackReport> list = reportDao.getPackageByTheMostCorrectAnswer();
		
		//Load file and compile it
		File file = ResourceUtils.getFile("classpath:report/MostCorrectPackage.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put("CreatedBy", "Lawencon International");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);
		
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,fileStorageLocation+File.separator+"MostCorrectPackage.html");
			fileName = "MostCorrectPackage.html";
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,fileStorageLocation+File.separator+"MostCorrectPackage.pdf");
			fileName = "MostCorrectPackage.pdf";
		}
		
		return fileName;
	}
	
	public String reportGetPackageByTheMostWrongAnswer(String reportFormat) throws Exception{
		
		String fileName = "";
		
		//Get the list from dao
		List<PojoPackReport> list = reportDao.getPackageByTheMostWrongAnswer();
		
		//Load file and compile it
		File file = ResourceUtils.getFile("classpath:report/MostWrongPackage.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put("CreatedBy", "Lawencon International");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);
		
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,fileStorageLocation+File.separator+"MostWrongPackage.html");
			fileName = "MostWrongPackage.html";
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,fileStorageLocation+File.separator+"MostWrongPackage.pdf");
			fileName = "MostWrongPackage.pdf";
		}
		
		return fileName;
	}
	
}
