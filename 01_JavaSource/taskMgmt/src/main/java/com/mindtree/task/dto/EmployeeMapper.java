package com.mindtree.task.dto;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.task.model.Employee;
import com.mindtree.task.model.Persistable;

public class EmployeeMapper {
	
	private EmployeeMapper(){
		throw new IllegalStateException("Utility class");
	}
	
	public static List<EmployeeDTO> toDTOList(List<Persistable> entitiesList){
		List<EmployeeDTO> empDTOList = new ArrayList<> ();
		
		for(Persistable obj: entitiesList){
			Employee emp = (Employee) obj;
			
			EmployeeDTO empDTO = toDTO(emp);
			empDTOList.add(empDTO);
		}
		
		return empDTOList;
	}
	
	public static List<Persistable> toEntityList(List<EmployeeDTO> empDTOList){
		
		List<Persistable> empList = new ArrayList<>();
		
		for(EmployeeDTO empDTO: empDTOList){
			Employee emp = (Employee) toEntity(empDTO);
			empList.add(emp);
		}
		
		return empList;
	}
	
	
	public static EmployeeDTO toDTO(Employee emp){
		
		EmployeeDTO empDTO = new EmployeeDTO();
		empDTO.setMid(emp.getMid());
		empDTO.setName(emp.getName());
		empDTO.setJoinDate(emp.getJoinDate());
		empDTO.setEmailId(emp.getEmailId());
		empDTO.setProject(emp.getProject());
		
		return empDTO;
	}
	
	public static Persistable toEntity(EmployeeDTO empDTO){
		
		Employee emp = new Employee();
		emp.setMid(empDTO.getMid());
		emp.setName(empDTO.getName());
		emp.setJoinDate(empDTO.getJoinDate());
		emp.setProject(empDTO.getProject());
		emp.setEmailId(empDTO.getEmailId());
		
		return emp;
	}

}
