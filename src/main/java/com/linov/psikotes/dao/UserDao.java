package com.linov.psikotes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.User;

@Repository("userDao")
public class UserDao extends CommonDao {

	@Transactional
	public void save (User user) {
		super.entityManager.merge(user);
	}
	
	public void delete(String id) {
		User user= findById(id);
		user.setActiveState("inactive");
		super.entityManager.merge(user);
//		super.entityManager.remove(role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> getAll() {
		List<User> list = super.entityManager
				.createQuery("from User where active_state=:status and role_id=:role order by timestamp desc")
				.setParameter("status", "active")
				.setParameter("role", "role2")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<User>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public User findById(String id) {
		List<User> list = super.entityManager
				.createQuery("from User where user_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new User();
		else
			return (User)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public User findBK(String password, String profileId) {
		List<User> list = super.entityManager
				.createQuery("from User where password=:pass or profile_id=:profile")
				.setParameter("pass", password)
				.setParameter("profile", profileId)
				.getResultList();
		if(list.size()==0)
			return new User();
		else
			return (User)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public User findByUsername(String username) {
		List<User> list = super.entityManager
				.createQuery("from User where username = :field1 and role_id = :field2")
				.setParameter("field1", username)
				.setParameter("field2", "role2")
				.getResultList();
		if(list.size()==0)
			return new User();
		else
			return (User)list.get(0);
	}
	
}
