package com.linov.psikotes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.Role;

@Repository("roleDao")
public class RoleDao extends CommonDao {

	@Transactional
	public void save (Role role) {
		super.entityManager.merge(role);
	}
	
	public void delete(String id) {
		Role role = findById(id);
		role.setActiveState("inactive");
		super.entityManager.merge(role);
//		super.entityManager.remove(role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Role> getAll() {
		List<Role> list = super.entityManager
				.createQuery("from Role where active_state=:status")
				.setParameter("status", "active")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<Role>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Role findById(String id) {
		List<Role> list = super.entityManager
				.createQuery("from Role where role_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new Role();
		else
			return (Role)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Role findByCode(String code) {
		List<Role> list = super.entityManager
				.createQuery("from Role where role_code=:code")
				.setParameter("code", code)
				.getResultList();
		if(list.size()==0)
			return new Role();
		else
			return (Role)list.get(0);
	}
	
}
