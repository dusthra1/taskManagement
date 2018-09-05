package com.mindtree.task.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.task.constants.ApplicationConstants;
import com.mindtree.task.constants.MessageCode;
import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.dao.TaskDAO;
import com.mindtree.task.dto.EmployeeDTO;
import com.mindtree.task.dto.EmployeeMapper;
import com.mindtree.task.dto.FileModelDTO;
import com.mindtree.task.dto.FileModelMapper;
import com.mindtree.task.dto.ProjectDTO;
import com.mindtree.task.dto.ProjectMapper;
import com.mindtree.task.dto.TaskDTO;
import com.mindtree.task.dto.TaskMapper;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.exception.DAOException;
import com.mindtree.task.model.Employee;
import com.mindtree.task.model.FileModel;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.model.Project;
import com.mindtree.task.model.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	private static final Logger log = Logger.getLogger(TaskServiceImpl.class);
	
	@Autowired
	private TaskDAO  taskDAO;	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUsersLoginStatus()  {
		try {			
			Map<String,Object> params = new HashMap<>();
			params.put("status_n", ApplicationConstants.NO);
			params.put("status_y", ApplicationConstants.YES);
			taskDAO.updateRecords(NamedQueryConstants.UPDATE_USERS_LOGIN_STATUS, params);	
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting employees " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while getting employees " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveProject(ProjectDTO projDTO) {

		try {
			Project proj = (Project) ProjectMapper.toEntity(projDTO);
			taskDAO.saveEntity(proj);
				
		} catch (DAOException daoEx) {
			log.error("Exception occured while saving project " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while saving project " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ProjectDTO getProject(Integer projId) {
		ProjectDTO projDTO = null;
		Project proj = (Project)taskDAO.getEntity(Project.class, projId);
		projDTO = ProjectMapper.toDTO(proj);
		return projDTO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectDTO> getAllProjects() {
		List<Persistable> allProjList = null;
		List<ProjectDTO> allProjDTOList = null;
		try {
			allProjList = taskDAO.findRecords(NamedQueryConstants.ALL_PROJECTS, null);
			allProjDTOList = ProjectMapper.toDTOList(allProjList);
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting projects " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while getting projects " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}
		return allProjDTOList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectDTO> getAllProjects(String projName) {
		List<Persistable> allProjList = null;
		List<ProjectDTO> allProjDTOList = null;
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("projName", "%"+projName+"%");
			
			allProjList = taskDAO.findRecords(NamedQueryConstants.PROJECTS_BY_LIKE_NAME, params);	
			allProjDTOList = ProjectMapper.toDTOList(allProjList);
			
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting projects " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while getting projects " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}
		return allProjDTOList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProject(ProjectDTO projDTO) {
		
		try {
			Project proj = (Project) ProjectMapper.toEntity(projDTO);
			taskDAO.deleteEntity(proj);
				
		} catch (DAOException daoEx) {
			log.error("Exception occured while deleting project " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while deleting project " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveEmployee(EmployeeDTO empDTO) {

		try {
			Employee emp = (Employee) EmployeeMapper.toEntity(empDTO);
			taskDAO.saveEntity(emp);
				
		} catch (DAOException daoEx) {
			log.error("Exception occured while saving employee " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while saving employee " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public EmployeeDTO getEmployee(String id) {
		EmployeeDTO empDTO = null;
		Employee emp = (Employee) taskDAO.getEntity(Employee.class,id);
		empDTO = EmployeeMapper.toDTO(emp);
		return empDTO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<EmployeeDTO> getProjEmployees(Integer projId) {
		List<Persistable> empList = null;
		List<EmployeeDTO> empDTOList = null;
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("projId", projId);
			
			empList = taskDAO.findRecords(NamedQueryConstants.PROJECT_EMPLOYEES, params);	
			empDTOList = EmployeeMapper.toDTOList(empList);
			
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting employees " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while getting employees " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}		
		return empDTOList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<EmployeeDTO> getEmployeesForMids(List<String> mIds) {
		List<Persistable> empList = null;
		List<EmployeeDTO> empDTOList = null;
		try {
			if(mIds != null && !mIds.isEmpty()){
				Map<String,Object> params = new HashMap<>();
				params.put("mIds", mIds);
				
				empList = taskDAO.findRecords(NamedQueryConstants.EMPLOYEES_FOR_IDS, params);	
				empDTOList = EmployeeMapper.toDTOList(empList);
			}
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting employees " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while getting employees " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}		
		return empDTOList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<EmployeeDTO> getAllEmployees()  {
		List<Persistable> empList = null;
		List<EmployeeDTO> empDTOList = null;
		try {
			empList = taskDAO.findRecords(NamedQueryConstants.ALL_EMPLOYEES, null);
			empDTOList = EmployeeMapper.toDTOList(empList);
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting employees " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while getting employees " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}		
		return empDTOList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteEmployee(EmployeeDTO empDTO) {
		
		try {
			Employee emp = (Employee) EmployeeMapper.toEntity(empDTO);
			taskDAO.deleteEntity(emp);
				
		} catch (DAOException daoEx) {
			log.error("Exception occured while deleting employee " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while deleting employee " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public TaskDTO saveTask(TaskDTO taskdto) {
		Task savedTask = null;
		try {
			Task task = (Task) TaskMapper.toEntity(taskdto);
			savedTask = (Task) taskDAO.saveEntity(task);
				
		} catch (DAOException daoEx) {
			log.error("Exception occured while saving task " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while saving task " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}
		return TaskMapper.toDTO(savedTask);
	}	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<TaskDTO> getAllTasks(Integer projId)  {
		List<Persistable> allTasksList = null;
		List<TaskDTO> allTasksDTOList = null;
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("projId", projId);
			
			allTasksList = taskDAO.findRecords(NamedQueryConstants.ALL_TASKS_FOR_PROJ, params);
			allTasksDTOList=TaskMapper.toDTOList(allTasksList);
			
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting tasks " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while getting tasks " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}
		return allTasksDTOList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveFile(FileModelDTO fileDTO) {

		try {
			FileModel fileModel = FileModelMapper.toEntity(fileDTO);
			taskDAO.saveEntity(fileModel);
				
		} catch (DAOException daoEx) {
			log.error("Exception occured while saving fileModel " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while saving fileModel " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public FileModelDTO getFile(Integer fileId) {
		FileModelDTO fileDTO = null;
		FileModel file= (FileModel) taskDAO.getEntity(FileModel.class, fileId);
		if(file!=null){
			fileDTO = FileModelMapper.toDTO(file);
		}
		return fileDTO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<FileModelDTO> getAllFiles() {
		List<Persistable> filesList = null;
		List<FileModelDTO> fileDTOList = null;
		try{
			filesList = taskDAO.findRecords(NamedQueryConstants.ALL_FILES, null);
			fileDTOList = FileModelMapper.toDTOList(filesList);
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting Files " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while getting Files " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}	
		return fileDTOList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteFile(FileModelDTO fileDTO) {
		
		try {
			FileModel fileModel = FileModelMapper.toEntity(fileDTO);
			taskDAO.deleteEntity(fileModel);
				
		} catch (DAOException daoEx) {
			log.error("Exception occured while deleting fileModel " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while deleting fileModel " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}
	}
	
	/*@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Persistable find(Class entityobj, int key){		
		Persistable obj;
		try {
			obj = taskDAO.getEntity(entityobj, key);
			
		} catch (DAOException daoEx) {
			log.error("Exception occured while finding entity " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while finding entity " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}	
		return obj;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Persistable find(Class entityobj, String key)  {
		Persistable obj;
		try {
			obj = taskDAO.getEntity(entityobj, key);
			
		}catch (DAOException daoEx) {
			log.error("Exception occured while finding entity " + daoEx.getMessage());
			throw new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);

		} catch (Exception ex) {
			log.error("Exception occured while finding entity " + ex.getMessage());
			throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
		}		
		return obj;
	}*/
}
