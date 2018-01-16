package com.mindtree.task.dto;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.task.model.Employee;
import com.mindtree.task.model.Persistable;

public class EmployeeMapper {
	
	public static List<EmployeeDTO> _toDTOList(List<Persistable> entitiesList){
		List<EmployeeDTO> empDTOList = new ArrayList<EmployeeDTO> ();
		
		for(Persistable obj: entitiesList){
			Employee emp = (Employee) obj;
			
			EmployeeDTO empDTO = new EmployeeDTO();
			empDTO.setMid(emp.getMid());
			empDTO.setName(emp.getName());
			empDTO.setJoinDate(emp.getJoinDate());
			empDTO.setEmailId(emp.getEmailId());
			empDTO.setProject(emp.getProject());
			empDTOList.add(empDTO);
		}
		
		return empDTOList;
	}

}
