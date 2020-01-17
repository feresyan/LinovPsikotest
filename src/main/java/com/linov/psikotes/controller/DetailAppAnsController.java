package com.linov.psikotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linov.psikotes.entity.DetailApplicantAnswer;
import com.linov.psikotes.entity.HeaderApplicantAnswer;
import com.linov.psikotes.entity.PackageQuestion;
import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.DetailAppAnsService;
import com.linov.psikotes.service.HeaderAppAnsService;
import com.linov.psikotes.service.PackageQuestionService;

@RestController
@RequestMapping("/applicant-answer/detail")
public class DetailAppAnsController {

	@Autowired
	private HeaderAppAnsService hAppAnsService;
	
	@Autowired
	private DetailAppAnsService dAppAnsService;
	
	@Autowired
	private PackageQuestionService pqService;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody List<DetailApplicantAnswer> dAppAns) throws ErrorException{
		try {
			
			//make var to store total points from all question
			Integer totalTruePoints = 0;
			
			//Make var to store total question
			Integer totalQuestion = 0;
			
			for (DetailApplicantAnswer data : dAppAns) {
				
				//sum total question
				totalQuestion+=1;
				
				//get package question record to get answer type
				PackageQuestion pq =  pqService.findById(data.getPackQuestion().getPackageQuestionId());
				
				//make temporary var for total points
				Integer tempPoints = 0;
				
				if(pq.getQuestion().getQuestionType().getAnswerType().equalsIgnoreCase("Pilihan ganda")) {
					if(pq.getQuestion().getCorrectAnswer().getAnswer1() != null && pq.getQuestion().getCorrectAnswer().getAnswer2() != null) {
						
						//sum the points
						totalTruePoints+=10;
						
						if(pq.getQuestion().getCorrectAnswer().getAnswer1().equalsIgnoreCase(data.getAppAnswer().getAnswer1())) {
							tempPoints+=5;
							totalTruePoints+=5;
						} else if (pq.getQuestion().getCorrectAnswer().getAnswer2().equalsIgnoreCase(data.getAppAnswer().getAnswer2())) {
							tempPoints+=5;
						}
					}
					else 
					{
						//sum the points
						totalTruePoints+=10;
						
						if(pq.getQuestion().getCorrectAnswer().getAnswer1().equalsIgnoreCase(data.getAppAnswer().getAnswer1())) {
							tempPoints+=10;
						}
					}
					//set point for question for multiple choice question
					data.setPoint(tempPoints);
					dAppAnsService.insertDetail(data);
				}//
				else 
				{
					//set point for question to 0 for essay question
					data.setPoint(tempPoints);
					dAppAnsService.insertDetail(data);
				}
				
			}
			
			//Get Header applicant answer record
			HeaderApplicantAnswer headerAppAns = hAppAnsService.findById(dAppAns.get(0).getHeaderAppAnswer().getApplicantAnswerId());
			
			//Get all answer for specific user
			List<DetailApplicantAnswer> listDetail = dAppAnsService.getAllDetailByAppAnsId(headerAppAns.getApplicantAnswerId());
			
			//make temporary var for storing total points
			Integer totalPoints = 0;
			for (DetailApplicantAnswer detail : listDetail) {
				totalPoints+= detail.getPoint();
			}
			
			//Set total points from sum point
			headerAppAns.setTotalPoints(totalPoints);
			
			//set status from total points that user get
			//get average
			Integer avg = totalTruePoints / totalQuestion;
			Integer kkm = avg * 75 /100;
			if((totalPoints/totalQuestion) >= kkm) {
				headerAppAns.setStatus("Lulus");
			} else {
				headerAppAns.setStatus("Tidak lulus");
			}
			hAppAnsService.updateHeaderApplicantAnswer(headerAppAns);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Detail Berhasil Ditambah");
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody DetailApplicantAnswer dAppAns) throws ErrorException{
		try {
			dAppAnsService.updateProfile(dAppAns);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Detail Berhasil Diperbarui");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) throws ErrorException {
		try {
			dAppAnsService.deleteProfile(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Detail Berhasil Dihapus");
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable String id) throws ErrorException {
		return ResponseEntity.ok(dAppAnsService.findById(id));
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllUser() throws ErrorException {
		return ResponseEntity.ok(dAppAnsService.getAllDetail());
	}
	
}
