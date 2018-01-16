package com.mindtree.task.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.task.model.Employee;
import com.mindtree.task.model.Project;

public class TaskDTO {
	
	private int taskId;
	private String taskName;
	private String description;
	private Date startDate;
	private Date dueDate;
	private Project project;
	
	private List<Employee> employeesList = new ArrayList<Employee>();

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

	public Project getProject() {
		return project;
	}

	public List<Employee> getEmployeesList() {
		return employeesList;
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

	public void setProject(Project project) {
		this.project = project;
	}

	public void setEmployeesList(List<Employee> employeesList) {
		this.employeesList = employeesList;
		/*Code Smell: for loop here is to avoid LazyInitializationException */
		for(Employee emp: employeesList){
			emp.getMid();break;
		}
		
	}

}
