package com.mindtree.task.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.constants.QueryConstants;

@Entity
@Table(name = "FILES_UPLOAD")
@NamedQueries(value = { @NamedQuery(name = NamedQueryConstants.ALL_FILES, query = QueryConstants.ALL_FILES)
})
public class FileModel implements Persistable{
	
	
	private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue
	 @Column(name = "FILE_ID")
	 private int id;
	 
	 @Column(name = "FILE_NAME")
	 private String fileName;
	 
	 @Column(name = "FILE_DATA")
	 private byte[] data;
	 
	 @Column(name = "CONTENT_TYPE")
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

	public void setId(int id) {
		this.id = id;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	 
	 

}
