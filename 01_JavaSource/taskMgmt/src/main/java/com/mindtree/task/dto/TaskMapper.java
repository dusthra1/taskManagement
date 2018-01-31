package com.mindtree.task.dto;

import java.util.ArrayList;
import java.util.List;

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
			
			TaskDTO taskDTO = new TaskDTO();
			taskDTO.setProject(task.getProject());
			taskDTO.setTaskId(task.getTaskId());
			taskDTO.setTaskName(task.getTaskName());
			taskDTO.setDescription(task.getDescription());
			taskDTO.setStartDate(task.getStartDate());
			taskDTO.setDueDate(task.getDueDate());
			taskDTO.setEmployeesList(task.getEmployeesList());
			taskDTOList.add(taskDTO);
		}
		
		return taskDTOList;
	}
	
	public static Task toEntity(TaskDTO taskdto){
		
		Task task = new Task();
		task.setProject(taskdto.getProject());
		task.setTaskName(taskdto.getTaskName());
		task.setDescription(taskdto.getDescription());
		task.setStartDate(taskdto.getStartDate());
		task.setDueDate(taskdto.getDueDate());
		task.setEmployeesList(taskdto.getEmployeesList());
		
		return task;
	}

}
