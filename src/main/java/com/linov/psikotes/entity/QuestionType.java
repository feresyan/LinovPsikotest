package com.linov.psikotes.entity;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_m_question_type", uniqueConstraints = @UniqueConstraint(columnNames = {"question_type_title"}))
public class QuestionType {
	
	@Id
	@GeneratedValue(generator="UUID")
	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	@Column(name="question_type_id")
	private String questionTypeId;
	
	@Column(name="question_type_title")
	private String questionTypeTitle;
	
	@Column(name="amount_of_time")
	private Time amountOfTime;
	
	@Column(name="active_state")
	private String activeState;

	public String getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(String questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getQuestionTypeTitle() {
		return questionTypeTitle;
	}

	public void setQuestionTypeTitle(String questionTypeTitle) {
		this.questionTypeTitle = questionTypeTitle;
	}

	public Time getAmountOfTime() {
		return amountOfTime;
	}

	public void setAmountOfTime(Time amountOfTime) {
		this.amountOfTime = amountOfTime;
	}

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}
	
}
