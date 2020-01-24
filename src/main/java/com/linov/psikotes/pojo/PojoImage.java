package com.linov.psikotes.pojo;

public class PojoImage {
	
	private byte[] imgByte;
	private String imgType;
	private String imgName;
	
	
	public PojoImage(byte[] imgByte, String imgType, String imgName) {
		super();
		this.imgByte = imgByte;
		this.imgType = imgType;
		this.imgName = imgName;
	}
	
	public byte[] getImgByte() {
		return imgByte;
	}
	public void setImgByte(byte[] imgByte) {
		this.imgByte = imgByte;
	}
	public String getImgType() {
		return imgType;
	}
	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
	
}
