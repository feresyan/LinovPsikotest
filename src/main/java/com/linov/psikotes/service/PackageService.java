package com.linov.psikotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linov.psikotes.entity.Package;
import com.linov.psikotes.dao.PackageDao;

@Service("packageService")
public class PackageService {
	
	@Autowired
	private PackageDao packageDao;

	public List<Package> getAllPackage(){
		List<Package> list = packageDao.getAll();
		return list;
	}
	
	public Package findById(String id) {
		Package profile = packageDao.findById(id);
		return profile;
	}
	
	public Package insertPackage(Package pack) throws Exception{
		try {
			
			//Check if id null
			valIdNull(pack);
			
			//Check if package name not null
			valBkNotNull(pack);
			
			//Check if package name not exist in DB
			valBkNotExist(findByName(pack.getPackageName()));
			
			//Check if nonBk not null
			valNonBk(pack);
			
			//Save
			return packageDao.save(pack);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void updatePackage(Package pack) throws Exception{
		try {
			//get old data of package from DB
			Package oldPack = findById(pack.getPackageId());
			
			//Check id not null
			valIdNotNull(pack);
			
			//Check if id is exist in DB
			valIdExist(oldPack.getPackageId());
			
			//Check if package name not null
			valBkNotNull(pack);
			
			//Check if package name not getting replaced
			valBkNotChange(oldPack, pack);
			
			//Check if nonBK not null
			valNonBk(pack);
			
			//Save
			packageDao.save(pack);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public void deletePackage(String id) throws Exception{
		try {
			//Check if id is exist in DB
			valIdExist(id);
			
			//Delete
			packageDao.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	public Package findByName(String title) {
		return packageDao.findByTitle(title);
	}
	
	// VALIDASI POST
	
 	private static Exception valIdNull(Package p) throws Exception{
 		if(p.getPackageId() !=null ) {
 			throw new Exception("Id harus kosong");
 		}
 		return null;
 	}
 	
 	private static Exception valBkNotNull(Package p) throws Exception{
 		if (p.getPackageName() == null || p.getPackageName().trim().equals("") ) 
 		{
 			throw new Exception("Package name tidak boleh kosong");
 		}
 		return null;
 	}
 	
 	private static Exception valBkNotExist(Package p) throws Exception{		
 		if(p.getPackageId()!=null) {
 			throw new Exception("Package sudah terdaftar");
 		}
 		return null;
 	}
 	
 	private static Exception valNonBk(Package p) throws Exception{
 		if( p.getActiveState() == null || p.getActiveState().trim().equals("")) 
 		{
 			throw new Exception("Active state tidak boleh kosong");
 		}
 		return null;
 	}
 	
 	// VAlIDASI PUT (valIdNotNull,valIdExist, valBkNotNull, valBkNotChange, valNonBk)
 	
 	private static Exception valIdNotNull(Package p) throws Exception{
 		if(p.getPackageId() == null || p.getPackageId().trim().equals("") ) {
 			throw new Exception("Id tidak boleh kosong");
 		}
 		return null;
 	}
 	
 	private static Exception valIdExist(String id) throws Exception{
 		if(id == null) {
 			throw new Exception("Package tidak ditemukan!");
 		}
 		return null;
 	}
 	
 	private static Exception valBkNotChange(Package oldP, Package newP) throws Exception{
 		if( !oldP.getPackageName().equalsIgnoreCase(newP.getPackageName())) {
 			throw new Exception("UC tidak dapat diubah!");
 		}
 		return null;
 	}
 	
 	// VALIDASI DELETE ( valIdExist )

}
