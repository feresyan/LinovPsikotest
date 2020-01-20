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
@Table(name = "tbl_assign_question", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","package_id"}))
public class AssignQuestion {

	@Id
	@Column(name="assign_question_id")
	@GeneratedValue(generator="UUID")
	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	private String assignQuestionId;
	
	@ManyToOne
	@JoinColumn(name = "user_id",referencedColumnName = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "package_id",referencedColumnName = "package_id")
	private Package pack;

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

	public Package getPack() {
		return pack;
	}

	public void setPack(Package pack) {
		this.pack = pack;
	}

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}
	
}
