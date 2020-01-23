package com.linov.psikotes.pojo;

public class PojoReportCandidate {
	private String profileName;
	private String address;
	private String phone;
	private String email;
	private String questionTitle;
	private String questionTypeTitle;
	private Integer point;
	private Integer totalPoints;
	
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	
	public String getQuestionTypeTitle() {
		return questionTypeTitle;
	}
	public void setQuestionTypeTitle(String questionTypeTitle) {
		this.questionTypeTitle = questionTypeTitle;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	
	public Integer getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}
	
}
