package com.linov.psikotes.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linov.psikotes.exception.ErrorException;
import com.linov.psikotes.service.ReportService;


@RestController
@RequestMapping("/report")
@CrossOrigin("*")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
		
	@GetMapping("/candidate/{id}/{format}")
	public ResponseEntity<?> getReportByHeaderId(@PathVariable String id, @PathVariable String format, HttpServletRequest request) throws ErrorException, IOException {
		
		try {
			
			String fileName = reportService.candidateReport(format, id,request);
			System.out.println(fileName);
			
			// Load file as Resource
	        Resource resource = reportService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/question/correct/{format}")
	public ResponseEntity<?> getReportCorrectAnswer(@PathVariable String format, HttpServletRequest request) throws ErrorException {
		
		try {
			
			String fileName = reportService.reportCorrectQuestion(format);
			
			// Load file as Resource
	        Resource resource = reportService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/question/wrong/{format}")
	public ResponseEntity<?> getReportWrongAnswer(@PathVariable String format, HttpServletRequest request) throws ErrorException {
		
		try {
			
			String fileName = reportService.reportFalseQuestion(format);
			
			// Load file as Resource
	        Resource resource = reportService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/package-question/correct/{format}")
	public ResponseEntity<?> getReportCorrectAnsAtPack(@PathVariable String format, HttpServletRequest request) throws ErrorException {
		
		try {
			String fileName =  reportService.reportCorrectAnsAtPack(format);
			
			// Load file as Resource
	        Resource resource = reportService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/package-question/wrong/{format}")
	public ResponseEntity<?> getReportWrongAnsAtPack(@PathVariable String format, HttpServletRequest request) throws ErrorException {
		
		try {
			 
			String fileName =  reportService.reportWrongAnsAtPack(format);
			
			// Load file as Resource
	        Resource resource = reportService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
			 
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/package/correct/{format}")
	public ResponseEntity<?> getPackageByTheMostCorrectAnswer(@PathVariable String format, HttpServletRequest request) throws ErrorException {
		
		try {
			
			String fileName = reportService.reportGetPackageByTheMostCorrectAnswer(format);
			
			// Load file as Resource
	        Resource resource = reportService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/package/wrong/{format}")
	public ResponseEntity<?> getPackageByTheMostWrongAnswer(@PathVariable String format, HttpServletRequest request) throws ErrorException {
		
		try {
			String fileName = reportService.reportGetPackageByTheMostWrongAnswer(format);
			
			// Load file as Resource
	        Resource resource = reportService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
