package com.mindtree.task.dto;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.task.model.Employee;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.model.Task;

public class TaskMapper {
	
	private TaskMapper(){
		throw new IllegalStateException("Utility class");
	}
	
	public static List<TaskDTO> toDTOList(List<Persistable> entitiesList){
		List<TaskDTO> taskDTOList = new ArrayList<> ();
		
		for(Persistable obj: entitiesList){
			Task task = (Task) obj;			
			TaskDTO taskDTO = toDTO(task);			
			taskDTOList.add(taskDTO);
		}
		
		return taskDTOList;
	}
	
	public static Persistable toEntity(TaskDTO taskdto){
		
		Task task = new Task();
		task.setProject(ProjectMapper.toEntity(taskdto.getProject()));
		task.setTaskName(taskdto.getTaskName());
		task.setDescription(taskdto.getDescription());
		task.setStartDate(taskdto.getStartDate());
		task.setDueDate(taskdto.getDueDate());
		List<Persistable> empList = EmployeeMapper.toEntityList(taskdto.getEmployeeDTOList());
		for(Persistable obj: empList){
			Employee emp = (Employee)obj;
			task.getEmployeesList().add(emp);
		}
		
		return task;
	}
	
	public static TaskDTO toDTO(Task task){
		
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setProject(ProjectMapper.toDTO(task.getProject()));
		taskDTO.setTaskId(task.getTaskId());
		taskDTO.setTaskName(task.getTaskName());
		taskDTO.setDescription(task.getDescription());
		taskDTO.setStartDate(task.getStartDate());
		taskDTO.setDueDate(task.getDueDate());
		List<EmployeeDTO> empDTOList = new ArrayList<>();
		for(Employee emp: task.getEmployeesList()){
			empDTOList.add(EmployeeMapper.toDTO(emp));
		}		
		taskDTO.setEmployeeDTOList(empDTOList);
		
		return taskDTO;
	}
}
