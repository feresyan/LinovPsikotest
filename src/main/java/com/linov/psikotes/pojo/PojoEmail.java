package com.linov.psikotes.pojo;

import java.util.Map;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PojoEmail {
	
	private String subject;
	private String password;
	private String sendTo;
	private Map<String, String> model;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public Map<String, String> getModel() {
		return model;
	}
	public void setModel(Map<String, String> model) {
		this.model = model;
	}

	
	
	
}
