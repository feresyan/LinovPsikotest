package com.linov.psikotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.dao.RoleDao;
import com.linov.psikotes.entity.Role;

@Service("roleService")
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;

	public List<Role> getAllRole(){
		List<Role> list = roleDao.getAll();
		return list;
	}
	
	public Role findById(String id) {
		Role role = roleDao.findById(id);
		return role;
	}
	
	public void insertRole(Role role) throws Exception{
		try {
			//Check if roleId null or not 
			valIdNull(role);
			
			//Check if roleCode null or not
			valBkNotNull(role);
			
			//Check if role already exist in DB or not
			valBkNotExist(findByCode(role.getRoleCode()));
			
			//Check if nonBK null or not
			valNonBk(role);
			
			//Save
			roleDao.save(role);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateRole(Role role) throws Exception{
		try {
			//Get old data of role
			Role oldRole = findById(role.getRoleId());
			
			//Check if userId null or not
			valIdNotNull(role);
			
			//check if id already exist in DB or not
			valIdExist(oldRole.getRoleId());
			
			//Check if roleCode is null or not
			valBkNotNull(role);
			
			//Check if roleCode being replaced or not
			valBkNotChange(oldRole, role);
			
			//Check if nonBk null or not
			valNonBk(role);
			
			//Save
			roleDao.save(role);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteRole(String id) throws Exception{
		try {
			
			//Check if roleId is exist in DB
			valIdExist(id);
			
			//Delete
			roleDao.delete(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public Role findByCode(String code) {
		Role role = roleDao.findByCode(code);
		return role;
	}
	
	// VALIDASI POST
	
	private static Exception valIdNull(Role role) throws Exception{
		if(role.getRoleId()!=null ) {
			throw new Exception("Id harus kosong");
		}
		return null;
	}
	
	private static Exception valBkNotNull(Role role) throws Exception{
		if(role.getRoleCode()==null || role.getRoleCode().trim().equals("") ) {
			throw new Exception("Code tidak boleh kosong");
		}
		return null;
	}
	
	private static Exception valBkNotExist(Role role) throws Exception{		
		if(role.getRoleId()!=null) {
			throw new Exception("Role sudah terdaftar");
		}
		return null;
	}
	
	private static Exception valNonBk(Role role) throws Exception{
		if( role.getRoleName() == null || role.getRoleName().trim().equals("")
				|| role.getActiveState()==null || role.getActiveState()=="") {
			throw new Exception("Tidak boleh ada field yang kosong");
		}
		return null;
	}
	
	// VAlIDASI PUT (valIdNotNull,valIdExist, valBkNotNull, valBkNotChange, valNonBk)
	
	private static Exception valIdNotNull(Role role) throws Exception{
		if(role.getRoleId()==null || role.getRoleId()=="") {
			throw new Exception("Id tidak boleh kosong");
		}
		return null;
	}
	
	private static Exception valIdExist(String id) throws Exception{
		if(id == null) {
			throw new Exception("Role tidak ditemukan!");
		}
		return null;
	}
	
	private static Exception valBkNotChange(Role oldRole, Role newRole) throws Exception{
		if(!oldRole.getRoleCode().equalsIgnoreCase(newRole.getRoleCode())) {
			throw new Exception("UC tidak dapat diubah!");
		}
		return null;
	}
	
	// VALIDASI DELETE ( valIdExist )
}
