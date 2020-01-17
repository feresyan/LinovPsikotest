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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "tbl_detail_applicant_answer", uniqueConstraints = @UniqueConstraint(columnNames = {"applicant_answer_id","package_question_id"}))
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class DetailApplicantAnswer {

	@Id
	@Column(name="detail_answer_id")
	@GeneratedValue(generator="UUID")
	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	private String detailAnswerId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "applicant_answer_id",referencedColumnName = "applicant_answer_id",nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private HeaderApplicantAnswer headerAppAnswer;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "package_question_id",referencedColumnName = "package_question_id",nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private PackageQuestion packQuestion;
	
	@Type(type = "jsonb")
	@Column(columnDefinition ="applicant_answer", name = "applicant_answer")
	private Answer appAnswer;
	
	@Column(name = "point")
	private Integer point;
	
	public String getDetailAnswerId() {
		return detailAnswerId;
	}

	public void setDetailAnswerId(String detailAnswerId) {
		this.detailAnswerId = detailAnswerId;
	}

	public HeaderApplicantAnswer getHeaderAppAnswer() {
		return headerAppAnswer;
	}

	public void setHeaderAppAnswer(HeaderApplicantAnswer headerAppAnswer) {
		this.headerAppAnswer = headerAppAnswer;
	}

	public PackageQuestion getPackQuestion() {
		return packQuestion;
	}

	public void setPackQuestion(PackageQuestion packQuestion) {
		this.packQuestion = packQuestion;
	}

	public Answer getAppAnswer() {
		return appAnswer;
	}

	public void setAppAnswer(Answer appAnswer) {
		this.appAnswer = appAnswer;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
}
