package com.mindtree.task.form;

import java.util.List;

public class AddTaskForm {
	
	private int projId;
	private String taskName;
	private String taskDesc;
	private String startDate;
	private String dueDate;
	private List<String> empId;
	public int getProjId() {
		return projId;
	}
	public void setProjId(int projId) {
		this.projId = projId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public List<String> getEmpId() {
		return empId;
	}
	public void setEmpId(List<String> empId) {
		this.empId = empId;
	}
	
	
	

}
