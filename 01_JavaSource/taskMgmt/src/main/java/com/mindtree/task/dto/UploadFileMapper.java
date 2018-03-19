package com.mindtree.task.dto;

import com.mindtree.task.model.UploadFile;

public class UploadFileMapper {
	
	private UploadFileMapper(){
		throw new IllegalStateException("Utility class");
	}
	
	public static UploadFileDTO toDTO(UploadFile file){
		
		UploadFileDTO fileDTO = new UploadFileDTO();
		
		fileDTO.setId(file.getId());
		fileDTO.setFileName(file.getFileName());
		fileDTO.setData(file.getData());
		fileDTO.setContentType(file.getContentType());
		
		return fileDTO;
	}

}
