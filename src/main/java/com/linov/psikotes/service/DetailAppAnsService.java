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
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.linov.psikotes.dao.AssignQuestionDao;
import com.linov.psikotes.dao.DetailAppAnsDao;
import com.linov.psikotes.dao.HeaderAppAnsDao;
import com.linov.psikotes.dao.PackageQuestionDao;
import com.linov.psikotes.entity.AssignQuestion;
import com.linov.psikotes.entity.DetailApplicantAnswer;
import com.linov.psikotes.entity.HeaderApplicantAnswer;
import com.linov.psikotes.entity.PackageQuestion;
import com.linov.psikotes.pojo.PojoEmailForAdmin;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service("detailAppAnsService")
public class DetailAppAnsService {
	
	@Autowired
	private PackageQuestionDao pqDao;
	
	@Autowired
	private AssignQuestionDao assignDao;
	
	@Autowired
	private DetailAppAnsDao dAppAnsDao;
	
	@Autowired
	private HeaderAppAnsDao appAnsDao;
	
	@Autowired
	private HeaderAppAnsService hAppAnsService;

	@Autowired
	private PackageQuestionService pqService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	@Qualifier("emailConfigBean")
	private Configuration emailConfig;
	

	public List<DetailApplicantAnswer> getAllDetail(){
		List<DetailApplicantAnswer> list = dAppAnsDao.getAll();
		return list;
	}
	
	public List<DetailApplicantAnswer> getAllDetailByAppAnsId(String id){
		List<DetailApplicantAnswer> list = dAppAnsDao.getAllByAppAnsId(id);
		return list;
	}
	
	public List<DetailApplicantAnswer> getAllEssayQuestByAppAnsId(String id){
		List<DetailApplicantAnswer> list = dAppAnsDao.getAllEssayQuestByAppAnsId(id);
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
//			valNonBk(detailAns);
						
			//Save
			return dAppAnsDao.save(detailAns);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateDetail(DetailApplicantAnswer detailAns) throws Exception{
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
			
			//Get total question 
			Integer totalQuestion = dAppAnsDao.getTotalQuestByAppAnsId(detailAns.getHeaderAppAnswer().getApplicantAnswerId()).intValue();
			Integer totalAllPoints = totalQuestion * 10;
			Integer totalPointsByUser = dAppAnsDao.getTotalPointsByAppAnsId(detailAns.getHeaderAppAnswer().getApplicantAnswerId()).intValue();
			
			//Get Header applicant answer record
			HeaderApplicantAnswer headerAppAns = hAppAnsService.findById(detailAns.getHeaderAppAnswer().getApplicantAnswerId());
			
			//Set total points from sum point
			headerAppAns.setTotalPoints(totalPointsByUser);
			
			//set status from total points that user get
			//get average
			Integer avg = totalAllPoints / totalQuestion;
			Integer kkm = avg * 75 /100;
			
			if((totalPointsByUser/totalQuestion) >= kkm) {
				headerAppAns.setStatus("Lulus");
			} else {
				headerAppAns.setStatus("Tidak lulus");
			}
			
			hAppAnsService.updateHeaderApplicantAnswer(headerAppAns);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteDetail(String id) throws Exception{
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
	
	public void calculatePoints(List<DetailApplicantAnswer> dAppAns) throws Exception {
				
		for (DetailApplicantAnswer data : dAppAns) 
		{
			
			//get package question record to get answer type
			PackageQuestion pq =  pqService.findById(data.getPackQuestion().getPackageQuestionId());
			
			//make temporary var for total points
			Integer tempPoints = 0;
			
			if(pq.getQuestion().getQuestionType().getQuestionTypeTitle().equalsIgnoreCase("Pilihan Ganda Teks") || 
					pq.getQuestion().getQuestionType().getQuestionTypeTitle().equalsIgnoreCase("Pilihan Ganda Teks 2") ||
					pq.getQuestion().getQuestionType().getQuestionTypeTitle().equalsIgnoreCase("Pilihan Ganda Gambar") || 
					pq.getQuestion().getQuestionType().getQuestionTypeTitle().equalsIgnoreCase("Pilihan Ganda Gambar 2")) {
				if(pq.getQuestion().getCorrectAnswer().getAnswer1() != null && pq.getQuestion().getCorrectAnswer().getAnswer2() != null) {
										
					if( ( pq.getQuestion().getCorrectAnswer().getAnswer1().equalsIgnoreCase(data.getAppAnswer().getAnswer1()) || pq.getQuestion().getCorrectAnswer().getAnswer1().equalsIgnoreCase(data.getAppAnswer().getAnswer2())) 
							&& (pq.getQuestion().getCorrectAnswer().getAnswer2().equalsIgnoreCase(data.getAppAnswer().getAnswer2()) || pq.getQuestion().getCorrectAnswer().getAnswer2().equalsIgnoreCase(data.getAppAnswer().getAnswer1())) ) {
						tempPoints+=10;
					}
				}
				else 
				{
					
					if (pq.getQuestion().getCorrectAnswer().getAnswer1().equalsIgnoreCase(data.getAppAnswer().getAnswer1())) {
						tempPoints+=10;	
						
					}
					
				}
				
				//set point for question for multiple choice question
				data.setPoint(tempPoints);
				insertDetail(data);
				
			}//
			else 
			{
				//set point for question to 0 for essay question
				data.setPoint(tempPoints);
				insertDetail(data);
			}
			
		}
		
		//Get Header applicant answer record
		HeaderApplicantAnswer headerAppAns = hAppAnsService.findById(dAppAns.get(0).getHeaderAppAnswer().getApplicantAnswerId());
		
		Integer totalQuestion = dAppAnsDao.getTotalQuestByAppAnsId(dAppAns.get(0).getHeaderAppAnswer().getApplicantAnswerId()).intValue();
		
		Integer totalTruePoints = totalQuestion * 10;
		
		//Get total points for specific user
		Integer totalPointsByUser = dAppAnsDao.getTotalPointsByAppAnsId(dAppAns.get(0).getHeaderAppAnswer().getApplicantAnswerId()).intValue();
		
		//Set total points from sum point
		headerAppAns.setTotalPoints(totalPointsByUser);
		
		//set status from total points that user get
		//get average
		Integer avg = totalTruePoints / totalQuestion;
		Integer kkm = avg * 75 /100;
		
		if((totalPointsByUser/totalQuestion) >= kkm) {
			headerAppAns.setStatus("Lulus");
		} else {
			headerAppAns.setStatus("Tidak lulus");
		}
		Date date =new Date(); 
		headerAppAns.setTimestamp(date);
		hAppAnsService.updateHeaderApplicantAnswer(headerAppAns);
		
		//Update package in tbl_assign_question that already answered to inactive
		//Cari userId
		String applicantUserId = dAppAns.get(0).getHeaderAppAnswer().getApplicantAnswerId();
		HeaderApplicantAnswer resultHA = appAnsDao.findById(applicantUserId);
		String userId = resultHA.getUser().getUserId();
		
		//Cari packId
		String packQuestId = dAppAns.get(0).getPackQuestion().getPackageQuestionId();
		PackageQuestion resultPQ = pqDao.findById(packQuestId);
		String packId = resultPQ.getPack().getPackageId();
		
		AssignQuestion aq = assignDao.findBK(userId, packId);
		assignDao.delete(aq.getAssignQuestionId());
		
		//Send email notification to admin if candidate already completed all assign question
		sendEmailToAdmin(headerAppAns);
	}
	
	public void sendEmailToAdmin(HeaderApplicantAnswer headerAppAns) throws Exception{
		Integer totalQuestion = dAppAnsDao.getTotalAssignQuestion(headerAppAns.getUser().getUserId());
		Integer totalInactive = dAppAnsDao.getTotalAssignQuestionThatInactive(headerAppAns.getUser().getUserId());
		
		//check if totalQuestion and totalInactive not 0 , coz if 0 then the candidate do not have assign question
		if((totalQuestion != 0 || totalQuestion != null) || (totalInactive != 0 || totalInactive != null)) {
			
			
			//Sending candidate info via email to admin
			PojoEmailForAdmin pojoEmailForAdmin = new PojoEmailForAdmin();
			pojoEmailForAdmin.setCandidateName(headerAppAns.getUser().getProfile().getProfileName());
			pojoEmailForAdmin.setEmail(headerAppAns.getUser().getProfile().getEmail());
			pojoEmailForAdmin.setPhone(headerAppAns.getUser().getProfile().getPhone());
			pojoEmailForAdmin.setTimestamp(headerAppAns.getTimestamp());
			pojoEmailForAdmin.setSendTo("feresyan@gmail.com");
			pojoEmailForAdmin.setSubject("Linov Psikotest - Lawencon International");
			sendEmail(pojoEmailForAdmin);
		}
		
	}
	
	//send email
    public void sendEmail(PojoEmailForAdmin pojoEmailForAdmin) throws MessagingException, IOException, TemplateException {

        Map<String, String> model = new HashMap<String, String>();
        model.put("candidateName", pojoEmailForAdmin.getCandidateName());
        model.put("email",pojoEmailForAdmin.getEmail());
        model.put("phone",pojoEmailForAdmin.getPhone());
        model.put("time", pojoEmailForAdmin.getTimestamp().toString());
        /**
         * Add below line if you need to create a token to verification emails and uncomment line:32 in "email.ftl"
         * model.put("token",UUID.randomUUID().toString());
         */

        pojoEmailForAdmin.setModel(model);


        //log.info("Sending Email to: " + mailModel.getTo());


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Template template = emailConfig.getTemplate("emailAdmin.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, pojoEmailForAdmin.getModel());

        mimeMessageHelper.setTo(pojoEmailForAdmin.getSendTo());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject(pojoEmailForAdmin.getSubject());
        mimeMessageHelper.setFrom("no-reply@gmail.com");


        javaMailSender.send(message);

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
		if( detailAns.getPoint() == null) {
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
