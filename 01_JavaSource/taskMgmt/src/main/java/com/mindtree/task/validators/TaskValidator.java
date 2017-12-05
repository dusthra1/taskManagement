package com.mindtree.task.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mindtree.task.form.AddTaskForm;

public class TaskValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		return AddTaskForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		
		AddTaskForm addTask = (AddTaskForm) target;
		if(addTask==null){
			 errors.reject("platform.null", "Platform object is null");
		}
		ValidationUtils.rejectIfEmpty(errors, "taskName", "task.required");
		ValidationUtils.rejectIfEmpty(errors, "startDate", "startDate.required");
		ValidationUtils.rejectIfEmpty(errors, "dueDate", "dueDate.required");
		
		 if(addTask.getProjId()==0){
				errors.rejectValue("projId", "required.proj");
		}
		 if(null == addTask.getEmpId() || addTask.getEmpId().size() ==0){
			 	errors.rejectValue("empId", "required.emp");
		 }
	}
	
	

}
