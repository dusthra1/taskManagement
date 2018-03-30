package com.mindtree.task.dto;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.task.model.FileModel;
import com.mindtree.task.model.Persistable;

public class FileModelMapper {
	
	private FileModelMapper(){
		throw new IllegalStateException("Utility class");
	}
	
	public static FileModelDTO toDTO(FileModel file){
		
		FileModelDTO fileDTO = new FileModelDTO();
		
		fileDTO.setId(file.getId());
		fileDTO.setFileName(file.getFileName());
		fileDTO.setData(file.getData());
		fileDTO.setContentType(file.getContentType());
		
		return fileDTO;
	}
	
	public static List<FileModelDTO> toDTOList(List<Persistable> filesList){
		
		List<FileModelDTO> filesDTOList = new ArrayList<>();
		
		for(Persistable obj: filesList){
			FileModel fileModel = (FileModel)obj;
			
			FileModelDTO fileModelDTO = new FileModelDTO();
			fileModelDTO.setId(fileModel.getId());
			fileModelDTO.setFileName(fileModel.getFileName());
			fileModelDTO.setContentType(fileModel.getContentType());
			//fileModelDTO.setData(fileModel.getData());
			
			filesDTOList.add(fileModelDTO);
		}
		
		return filesDTOList;
	}

}
