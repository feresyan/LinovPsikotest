package com.linov.psikotes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_m_package", uniqueConstraints = @UniqueConstraint(columnNames = {"package_name"}))
public class Package {

	@Id
	@Column(name="package_id")
	@GeneratedValue(generator="UUID")
	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	private String packageId;
	
	@Column(name="package_name")
	private String packageName;
	
	@Column(name="amountOfTime")
	private Integer amountOfTime;
	
	@Column(name="active_state")
	private String activeState;

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

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}

	public Integer getAmountOfTime() {
		return amountOfTime;
	}

	public void setAmountOfTime(Integer amountOfTime) {
		this.amountOfTime = amountOfTime;
	}

}
