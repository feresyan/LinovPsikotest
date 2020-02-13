package com.linov.psikotes.pojo;

import java.util.Date;

import com.linov.psikotes.entity.QuestionType;

public class PojoQuestion {
	
	private String questionId;
	private QuestionType questionType;
	private String questionTitle;
	private String questionDesc;
	private PojoListImg listImg;
	private PojoChoice choice;
	private Date timestamp;
	private String activeState;
	
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public QuestionType getQuestionType() {
		return questionType;
	}
	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getQuestionDesc() {
		return questionDesc;
	}
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
	public PojoListImg getListImg() {
		return listImg;
	}
	public void setListImg(PojoListImg listImg) {
		this.listImg = listImg;
	}
	public PojoChoice getChoice() {
		return choice;
	}
	public void setChoice(PojoChoice choice) {
		this.choice = choice;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getActiveState() {
		return activeState;
	}
	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}

}
