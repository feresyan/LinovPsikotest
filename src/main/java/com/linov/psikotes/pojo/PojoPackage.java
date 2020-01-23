package com.linov.psikotes.pojo;

import java.util.List;

import com.linov.psikotes.entity.Question;

public class PojoPackage {

	private String packageId;
	private String packageName;
	private Integer amountOfQuestion;
	private Integer amountOfTime;
	private List<Question> listQuestion;
	
	
	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public Integer getAmountOfQuestion() {
		return amountOfQuestion;
	}
	
	public void setAmountOfQuestion(Integer amountOfQuestion) {
		this.amountOfQuestion = amountOfQuestion;
	}
	
	public Integer getAmountOfTime() {
		return amountOfTime;
	}
	
	public void setAmountOfTime(Integer amountOfTime) {
		this.amountOfTime = amountOfTime;
	}

	public List<Question> getListQuestion() {
		return listQuestion;
	}

	public void setListQuestion(List<Question> listQuestion) {
		this.listQuestion = listQuestion;
	}
	
}
