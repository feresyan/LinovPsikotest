package com.linov.psikotes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.User;

@Repository("dashboardDao")
public class DashboardDao extends CommonDao {
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Integer getAllCandidate() {
		List<User> list = super.entityManager
				.createQuery("from User where lower(active_state) = 'active' and role_id = 'role2' ")
				.getResultList();
		if(list.size()==0) return 0;
		else {
			return list.size();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Integer getAllCandidateThatPass() {
		List<User> list = super.entityManager
				.createQuery("from HeaderApplicantAnswer where lower(status) = 'lulus'")
				.getResultList();
		if(list.size()==0) return 0;
		else {
			return list.size();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Integer getAllCandidateThatNotPass() {
		List<User> list = super.entityManager
				.createQuery("from HeaderApplicantAnswer where lower(status) = 'tidak lulus'")
				.getResultList();
		if(list.size()==0) return 0;
		else {
			return list.size();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Integer getTotalQuestion() {
		List<User> list = super.entityManager
				.createQuery("from Question where lower(active_state) = 'active'")
				.getResultList();
		if(list.size()==0) return 0;
		else {
			return list.size();
		}
		
	}
}
