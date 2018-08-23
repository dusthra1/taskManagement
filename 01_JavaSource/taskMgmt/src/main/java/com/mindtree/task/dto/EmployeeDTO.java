package com.mindtree.task.dto;

import java.util.Date;

public class EmployeeDTO {
	
	private String mid;
	private String name;
	private Date joinDate;
	private String emailId;
	private ProjectDTO project;
	
	public String getMid() {
		return mid;
	}
	public String getName() {
		return name;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public String getEmailId() {
		return emailId;
	}
	public ProjectDTO getProject() {
		return project;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public void setProject(ProjectDTO project) {
		this.project = project;
	}
	
	

}
