package com.linov.psikotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.DashboardDao;

@Service("dashboardService")
public class DashboardService {
	
	@Autowired
	private DashboardDao dashboardDao;
	
	
	public Integer getTotalCandidate() {
		return dashboardDao.getAllCandidate();
	}
	
	public Integer getTotalCandidateThatPass() {
		return dashboardDao.getAllCandidateThatPass();
	}
	
	public Integer getTotalCandidateThatNotPass() {
		return dashboardDao.getAllCandidateThatNotPass();
	}
	
	public Integer getTotalQuestion() {
		return dashboardDao.getTotalQuestion();
	}
}
