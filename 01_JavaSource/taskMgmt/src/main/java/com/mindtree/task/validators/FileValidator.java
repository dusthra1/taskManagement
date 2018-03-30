package com.mindtree.task.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mindtree.task.form.MultipartFileForm;

public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MultipartFileForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MultipartFileForm mFileModel = (MultipartFileForm) target;
		if(mFileModel == null){
			errors.reject("platform.null", "Platform object is null");
		}
		
		if (mFileModel.getFile() != null && mFileModel.getFile().isEmpty()){
            errors.rejectValue("file", "file.empty");
        }
		
		
	}

}
