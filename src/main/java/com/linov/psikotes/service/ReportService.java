package com.linov.psikotes.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.linov.psikotes.dao.DetailAppAnsDao;
import com.linov.psikotes.dao.ReportDao;
import com.linov.psikotes.entity.DetailApplicantAnswer;
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
	private String path;
	
	public String candidateReport(String reportFormat, String id) throws Exception{
		
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
		Path p = Paths.get(path);
		if(!Files.exists(p)) {
			try {
				Files.createDirectories(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Load file and compile it
		File file = ResourceUtils.getFile("classpath:report/candidate.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listReport);
		Map<String,Object> parameter = new HashMap<>();
		parameter.put("CreatedBy", "Lawencon International");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);
		
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"Candidate.html");
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"Candidate.pdf");
		}
		
		return new File(path+"Candidate.pdf").toURI().toString();

	}
	
	public String reportCorrectQuestion(String reportFormat) throws Exception{
		
		//Path for store the jasper
		String path = "D:\\";
		
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
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\MostCorrectAnswer.html");
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\MostCorrectAnswer.pdf");
		}
		
		return "Report Generated in path : " + path;
		
	}
	
	public String reportFalseQuestion(String reportFormat) throws Exception{
		
		//Path for store the jasper
		String path = "D:\\";
		
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
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\MostWrongAnswer.html");
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\MostWrongAnswer.pdf");
		}
		
		return "Report Generated in path : " + path;
		
	}
	
	public String reportCorrectAnsAtPack(String reportFormat) throws Exception{
		
		//Path for store the jasper
		String path = "D:\\";
		
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
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\MostCorrectAnsBaseOnPack.html");
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\MostCorrectAnsBaseOnPack.pdf");
		}
		
		return "Report Generated in path : " + path;

	}
	
	public String reportWrongAnsAtPack(String reportFormat) throws Exception{
		
		//Path for store the jasper
		String path = "D:\\";
		
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
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\MostWrongAnsBaseOnPack.html");
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\MostWrongAnsBaseOnPack.pdf");
		}
		
		return "Report Generated in path : " + path;

	}
	
	public String reportGetPackageByTheMostCorrectAnswer(String reportFormat) throws Exception{
		
		//Path for store the jasper
		String path = "D:\\";
		
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
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\MostCorrectPackage.html");
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\MostCorrectPackage.pdf");
		}
		
		return "Report Generated in path : " + path;
	}
	
	public String reportGetPackageByTheMostWrongAnswer(String reportFormat) throws Exception{
		
		//Path for store the jasper
		String path = "D:\\";
		
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
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\MostWrongPackage.html");
		}
		
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\MostWrongPackage.pdf");
		}
		
		return "Report Generated in path : " + path;
	}
	
}
