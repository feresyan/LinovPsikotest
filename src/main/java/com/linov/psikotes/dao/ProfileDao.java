package com.linov.psikotes.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.linov.psikotes.entity.Profile;

@Repository("profileDao")
public class ProfileDao extends CommonDao{

	@Transactional
	public Profile save (Profile profile) {
		return super.entityManager.merge(profile);
		
	}
	
	public void delete(String id) {
		Profile profile= findById(id);
//		profile.setActiveState("inactive");
		super.entityManager.merge(profile);
//		super.entityManager.remove(role);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Profile> getAll() {
		List<Profile> list = super.entityManager
				.createQuery("from Profile")
				.getResultList();
		if(list.size()==0) return null;
		else return (List<Profile>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Profile> findByName(String name) {
		List<Profile> list = super.entityManager
				.createQuery("from Profile where lower(profile_name) like concat('%', :nameSer, '%')")
				.setParameter("nameSer", name.toLowerCase())
				.getResultList();
		if(list.size()==0) return null;
		else return (List<Profile>)list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Profile findByEmail(String email) {
		List<Profile> list = super.entityManager
				.createQuery("from Profile where lower(email) like concat('%', :nameEmail, '%')")
				.setParameter("nameEmail", email.toLowerCase())
				.getResultList();
		if(list.size()==0) return null;
		else return (Profile)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Profile findByPhone(String phone) {
		List<Profile> list = super.entityManager
				.createQuery("from Profile where phone like concat('%', :phone, '%')")
				.setParameter("phone", phone)
				.getResultList();
		if(list.size()==0) return null;
		else return (Profile)list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Profile findById(String id) {
		List<Profile> list = super.entityManager
				.createQuery("from Profile where profile_id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size()==0)
			return new Profile();
		else
			return (Profile)list.get(0);
	}
	

	@SuppressWarnings("unchecked")
	@Transactional
	public Profile findBK(String phone, String email) {
		List<Profile> list = super.entityManager
				.createQuery("from Profile where phone=:phone or email=:email")
				.setParameter("phone", phone)
				.setParameter("email", email)
				.getResultList();
		if(list.size()==0)
			return new Profile();
		else
			return (Profile)list.get(0);
	}
	
}
