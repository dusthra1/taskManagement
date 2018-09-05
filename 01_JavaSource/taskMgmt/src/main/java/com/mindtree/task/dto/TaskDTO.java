package com.mindtree.task.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.task.model.Project;

public class TaskDTO {
	
	private int taskId;
	private String taskName;
	private String description;
	private Date startDate;
	private Date dueDate;
	private ProjectDTO project;
	
	private List<EmployeeDTO> EmployeeDTOList = new ArrayList<>();

	public int getTaskId() {
		return taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public ProjectDTO getProject() {
		return project;
	}

	public List<EmployeeDTO> getEmployeeDTOList() {
		return EmployeeDTOList;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public void setEmployeeDTOList(List<EmployeeDTO> EmployeeDTOsList) {
		this.EmployeeDTOList = EmployeeDTOsList;
		/*Code Smell: for loop here is to avoid LazyInitializationException */
		for(EmployeeDTO emp: EmployeeDTOsList){
			emp.getMid();break;
		}
		
	}

}
