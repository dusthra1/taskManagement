package com.mindtree.task.dto;

public class UploadFileDTO {
	
	private int id;
	private String fileName;
	private byte[] data;
	private String contentType;
	
	public int getId() {
		return id;
	}
	public String getFileName() {
		return fileName;
	}
	public byte[] getData() {
		return data;
	}
	public String getContentType() {
		return contentType;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	 
	 

}
