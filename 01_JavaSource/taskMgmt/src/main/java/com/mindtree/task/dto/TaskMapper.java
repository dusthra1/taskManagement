package com.mindtree.task.dto;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.task.model.Persistable;
import com.mindtree.task.model.Task;

public class TaskMapper {
	
	public static List<TaskDTO> _toDTOList(List<Persistable> entitiesList){
		List<TaskDTO> taskDTOList = new ArrayList<TaskDTO> ();
		
		for(Persistable obj: entitiesList){
			Task task = (Task) obj;
			
			TaskDTO taskDTO = new TaskDTO();
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

}
