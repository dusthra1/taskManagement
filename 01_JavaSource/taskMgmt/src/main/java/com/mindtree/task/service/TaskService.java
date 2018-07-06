package com.mindtree.task.service;

import java.util.List;
import java.util.Map;

import com.mindtree.task.dto.EmployeeDTO;
import com.mindtree.task.dto.ProjectDTO;
import com.mindtree.task.dto.TaskDTO;
import com.mindtree.task.dto.FileModelDTO;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.model.Task;

public interface TaskService {
	
	public void saveEntity(Persistable obj);
	
	public void saveRecord(String insertQry, Map<String,Object> params);
	
	public void deleteEntity(Persistable obj);
	
	public List<ProjectDTO> getAllProjects();
	
	public List<ProjectDTO> getAllProjects(String projName);
	
	public List<EmployeeDTO> getAllEmployees(Integer projId);
	
	public List<Persistable> getAllEmployees();
	
	public Task saveTaskDetails(TaskDTO taskdto);
	
	public Persistable find(Class entityobj, int key);
	
	public Persistable find(Class entityobj, String key);
	
	public List<TaskDTO> getAllTasks(Integer projId);
	
	public void updateUsersLoginStatus();
	
	public FileModelDTO getFile(Integer fileId);
	
	public List<FileModelDTO> getAllFiles();

}
