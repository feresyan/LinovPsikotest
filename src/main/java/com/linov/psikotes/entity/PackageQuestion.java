package com.linov.psikotes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "tbl_package_question", uniqueConstraints = @UniqueConstraint(columnNames = {"package_id","question_id"}))
public class PackageQuestion {
	
	@Id
	@Column(name="package_question_id")
	@GeneratedValue(generator="UUID")
	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	private String packageQuestionId;
	
	@ManyToOne
	@JoinColumn(name = "package_id",referencedColumnName = "package_id")
	private Package pack;
	
	@ManyToOne
	@JoinColumn(name = "question_id",referencedColumnName = "question_id")
	private Question question;
	
	@Column(name="active_state")
	private String activeState;

	public String getPackageQuestionId() {
		return packageQuestionId;
	}

	public void setPackageQuestionId(String packageQuestionId) {
		this.packageQuestionId = packageQuestionId;
	}

	public Package getPack() {
		return pack;
	}

	public void setPack(Package pack) {
		this.pack = pack;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}
	
	
}
