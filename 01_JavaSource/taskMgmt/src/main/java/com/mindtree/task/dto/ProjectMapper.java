package com.mindtree.task.dto;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.task.model.Persistable;
import com.mindtree.task.model.Project;

public class ProjectMapper {
	
	private ProjectMapper(){
		throw new IllegalStateException("Utility class");
	}
	
	public static List<ProjectDTO> toDTOList(List<Persistable> entitiesList){
		List<ProjectDTO> prjDTOList = new ArrayList<> ();
		
		for(Persistable obj: entitiesList){
			Project proj = (Project) obj;
			
			ProjectDTO projDTO = new ProjectDTO();
			projDTO.setId(proj.getId());
			projDTO.setName(proj.getName());
			projDTO.setDescription(proj.getDescription());
			prjDTOList.add(projDTO);
		}
		
		return prjDTOList;
	}

}
