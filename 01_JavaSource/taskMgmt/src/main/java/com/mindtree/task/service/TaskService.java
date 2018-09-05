package com.mindtree.task.service;

import java.util.List;

import com.mindtree.task.dto.EmployeeDTO;
import com.mindtree.task.dto.FileModelDTO;
import com.mindtree.task.dto.ProjectDTO;
import com.mindtree.task.dto.TaskDTO;

public interface TaskService {
	
	public void updateUsersLoginStatus();
	
	
	public void saveProject(ProjectDTO projDTO);
	
	public ProjectDTO getProject(Integer projId);
	
	public List<ProjectDTO> getAllProjects();
	
	public List<ProjectDTO> getAllProjects(String projName);
	
	public void deleteProject(ProjectDTO projDTO);
	
	
	public void saveEmployee(EmployeeDTO empDTO);
	
	public EmployeeDTO getEmployee(String id);
	
	public List<EmployeeDTO> getProjEmployees(Integer projId);
	
	public List<EmployeeDTO> getEmployeesForMids(List<String> mIds);
	
	public List<EmployeeDTO> getAllEmployees();
	
	public void deleteEmployee(EmployeeDTO empDTO);
	
	
	
	public TaskDTO saveTask(TaskDTO taskdto);
	
	public List<TaskDTO> getAllTasks(Integer projId);	
	
	
	
	public void saveFile(FileModelDTO fileDTO);
	
	public FileModelDTO getFile(Integer fileId);
	
	public List<FileModelDTO> getAllFiles();
	
	public void deleteFile(FileModelDTO fileDTO);
	
	
	//public Persistable find(Class entityobj, int key);
	
	//public Persistable find(Class entityobj, String key);

}
