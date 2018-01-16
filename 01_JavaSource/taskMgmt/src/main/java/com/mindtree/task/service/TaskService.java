package com.mindtree.task.service;

import java.util.List;

import com.mindtree.task.dto.EmployeeDTO;
import com.mindtree.task.dto.ProjectDTO;
import com.mindtree.task.dto.TaskDTO;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.form.AddTaskForm;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.model.Task;

public interface TaskService {
	
	public void saveEntity(Persistable obj) throws ApplicationException;
	
	public void updateEntity(Persistable obj) throws ApplicationException;
	
	public void deleteEntity(Persistable obj) throws ApplicationException;
	
	public List<ProjectDTO> getAllProjects() throws ApplicationException;
	
	public List<ProjectDTO> getAllProjects(String projName) throws ApplicationException;
	
	public List<EmployeeDTO> getAllEmployees(Integer projId) throws ApplicationException;
	
	public List<Persistable> getAllEmployees() throws ApplicationException;
	
	public Task saveTaskDetails(AddTaskForm taskForm) throws ApplicationException;
	
	public Persistable find(Class entityobj, int key) throws ApplicationException;
	
	public Persistable find(Class entityobj, String key) throws ApplicationException;
	
	public List<TaskDTO> getAllTasks(Integer projId) throws ApplicationException;
	
	public void updateUsersLoginStatus() throws ApplicationException;

}
