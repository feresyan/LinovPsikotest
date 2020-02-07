package com.linov.psikotes.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.PojoUser;
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
	public List<PojoUser> getAll() {
		List<User> list = super.entityManager
				.createQuery("from User where active_state=:status and role_id=:role order by timestamp desc")
				.setParameter("status", "active")
				.setParameter("role", "role2")
				.getResultList();
		if(list.size()==0) return null;
		else {
			List<PojoUser> listPojoUser = new ArrayList<PojoUser>();
			for (User data : list) {
				PojoUser pu = new PojoUser();
				pu.setUserId(data.getUserId());
				pu.setUsername(data.getUsername());
				pu.setRole(data.getRole());
				pu.setProfile(data.getProfile());
				listPojoUser.add(pu);
			}
			return (List<PojoUser>)listPojoUser;
		}
			
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
	public PojoUser findByUsername(String username) {
		List<User> list = super.entityManager
				.createQuery("from User where username = :field1")
				.setParameter("field1", username)
				.getResultList();
		if(list.size()==0)
			return new PojoUser();
		else
		{
			PojoUser pu = new PojoUser();
			pu.setUserId(list.get(0).getUserId());
			pu.setUsername(list.get(0).getUsername());
			pu.setRole(list.get(0).getRole());
			pu.setProfile(list.get(0).getProfile());
			return pu;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public User loadUserByUsername(String username) {
		List<User> list = super.entityManager
				.createQuery("from User where username = :field1")
				.setParameter("field1", username)
				.getResultList();
		if(list.size()==0)
			return new User();
		else
		{
			return list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PojoUser> findByProfileName(String name) {
		List<User> list = super.entityManager
				.createQuery("from User where lower(profile.profileName)  like concat('%', :field1, '%') and role_id = :field2")
				.setParameter("field1", name)
				.setParameter("field2", "role2")
				.getResultList();
		if(list.size()==0)
			return new ArrayList<PojoUser>();
		else
		{
			List<PojoUser> listPojoUser = new ArrayList<PojoUser>();
			
			for (int i = 0; i < list.size(); i++) {
				PojoUser pu = new PojoUser();
				pu.setUserId(list.get(0).getUserId());
				pu.setUsername(list.get(0).getUsername());
				pu.setRole(list.get(0).getRole());
				pu.setProfile(list.get(0).getProfile());
				listPojoUser.add(pu);
			}
			
			return listPojoUser;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PojoUser> findByEmail(String email) {
		List<User> list = super.entityManager
				.createQuery("from User where lower(profile.email) like concat('%', :nameEmail, '%')")
				.setParameter("nameEmail", email.toLowerCase())
				.getResultList();
		if(list.size()==0)
			return new ArrayList<PojoUser>();
		else
		{
			List<PojoUser> listPojoUser = new ArrayList<PojoUser>();
			
			for (int i = 0; i < list.size(); i++) {
				PojoUser pu = new PojoUser();
				pu.setUserId(list.get(0).getUserId());
				pu.setUsername(list.get(0).getUsername());
				pu.setRole(list.get(0).getRole());
				pu.setProfile(list.get(0).getProfile());
				listPojoUser.add(pu);
			}
			
			return listPojoUser;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<PojoUser> findByPhone(String phone) {
		List<User> list = super.entityManager
				.createQuery("from User where profile.phone like concat('%', :phone, '%')")
				.setParameter("phone", phone)
				.getResultList();
		if(list.size()==0)
			return new ArrayList<PojoUser>();
		else
		{
			List<PojoUser> listPojoUser = new ArrayList<PojoUser>();
			
			for (int i = 0; i < list.size(); i++) {
				PojoUser pu = new PojoUser();
				pu.setUserId(list.get(0).getUserId());
				pu.setUsername(list.get(0).getUsername());
				pu.setRole(list.get(0).getRole());
				pu.setProfile(list.get(0).getProfile());
				listPojoUser.add(pu);
			}
			
			return listPojoUser;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public User findByUsernameWithPassword(String username) {
		List<User> list = super.entityManager
				.createQuery("from User where username = :field1")
				.setParameter("field1", username)
				.getResultList();
		if(list.size()==0)
			return null;
		else
		{
			return list.get(0);
		}
	}
	
}
