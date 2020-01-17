package com.linov.psikotes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_assign_question", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","package_question_id"}))
public class AssignQuestion {

	@Id
	@Column(name="assign_question_id")
	@GeneratedValue(generator="UUID")
	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	private String assignQuestionId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id",referencedColumnName = "user_id",nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "package_question_id",referencedColumnName = "package_question_id",nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private PackageQuestion packageQuestion;

	@Column(name = "active_state")
	private String activeState;
	
	public String getAssignQuestionId() {
		return assignQuestionId;
	}

	public void setAssignQuestionId(String assignQuestionId) {
		this.assignQuestionId = assignQuestionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PackageQuestion getPackageQuestion() {
		return packageQuestion;
	}

	public void setPackageQuestion(PackageQuestion packageQuestion) {
		this.packageQuestion = packageQuestion;
	}

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}
	
}
