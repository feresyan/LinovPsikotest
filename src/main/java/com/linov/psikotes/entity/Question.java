package com.linov.psikotes.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "tbl_m_question", uniqueConstraints = @UniqueConstraint(columnNames = {"question_title"}))
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Question {

	@Id
	@Column(name="question_id")
	@GeneratedValue(generator="UUID")
	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	private String questionId;
	
	@OneToOne
	@JoinColumn(name = "question_type_id",referencedColumnName = "question_type_id",nullable = false)
	private QuestionType questionType;
	
	@Column(name="question_title")
	private String questionTitle;
	
	@Column(name="questionDesc")
	private String questionDesc;
	
	@Type(type = "jsonb")
	@Column(name="list_img")
	private ListImg listImg;
	
	@Type(type = "jsonb")
	@Column(columnDefinition ="data")
	private Choice choice;
	
	@Type(type = "jsonb")
	@Column(columnDefinition ="correct_answer")
	private Answer correctAnswer;
	
	@Column(name="timestamp")
	private Date timestamp;
	
	@Column(name="active_state")
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

	public Choice getChoice() {
		return choice;
	}

	public void setChoice(Choice choice) {
		this.choice = choice;
	}

	public Answer getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(Answer correctAnswer) {
		this.correctAnswer = correctAnswer;
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

	public ListImg getListImg() {
		return listImg;
	}

	public void setListImg(ListImg listImg) {
		this.listImg = listImg;
	}


}
