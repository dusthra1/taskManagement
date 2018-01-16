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
import com.mindtree.task.dto.ProjectDTO;
import com.mindtree.task.dto.ProjectMapper;
import com.mindtree.task.dto.TaskDTO;
import com.mindtree.task.dto.TaskMapper;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.exception.DAOException;
import com.mindtree.task.form.AddTaskForm;
import com.mindtree.task.model.Employee;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.model.Project;
import com.mindtree.task.model.Task;
import com.mindtree.task.util.TaskUtil;

@Service
public class TaskServiceImpl implements TaskService {
	
	private static final Logger log = Logger.getLogger(TaskServiceImpl.class);
	
	@Autowired
	private TaskDAO  taskDAO;	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveEntity(Persistable obj){
		if(obj !=null){
			try {
				taskDAO.saveEntity(obj);
				
			} catch (DAOException daoEx) {
				log.error("Exception occured while save entity " + daoEx.getMessage());
				ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
				throw ae;

			} catch (Exception ex) {
				log.error("Exception occured while save entity " + ex.getMessage());
				ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
				throw ae;
			}
		}		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEntity(Persistable obj) throws ApplicationException {
		if(obj !=null){
			try {
				taskDAO.updateEntity(obj);
				
			} catch (DAOException daoEx) {
				log.error("Exception occured while update entity " + daoEx.getMessage());
				ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
				throw ae;

			} catch (Exception ex) {
				log.error("Exception occured while update entity " + ex.getMessage());
				ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
				throw ae;
			}
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteEntity(Persistable obj){
		if(obj !=null){
			try {
				taskDAO.deleteEntity(obj);
			}catch (DAOException daoEx) {
				log.error("Exception occured while delete entity" + daoEx.getMessage());
				ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
				throw ae;

			} catch (Exception ex) {
				log.error("Exception occured while delete entity " + ex.getMessage());
				ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
				throw ae;
			}
		}
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<EmployeeDTO> getAllEmployees(Integer projId) {
		List<Persistable> empList = null;
		List<EmployeeDTO> empDTOList = null;
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("projId", projId);
			
			empList = taskDAO.findRecords(NamedQueryConstants.PROJECT_EMPLOYEES, params);	
			empDTOList = EmployeeMapper._toDTOList(empList);
			
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting employees " + daoEx.getMessage());
			ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
			throw ae;

		} catch (Exception ex) {
			log.error("Exception occured while getting employees " + ex.getMessage());
			ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
			throw ae;
		}		
		return empDTOList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Persistable> getAllEmployees() throws ApplicationException {
		List<Persistable> empList = null;
		try {
			
			empList = taskDAO.findRecords(NamedQueryConstants.ALL_EMPLOYEES, null);	
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting employees " + daoEx.getMessage());
			ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
			throw ae;

		} catch (Exception ex) {
			log.error("Exception occured while getting employees " + ex.getMessage());
			ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
			throw ae;
		}		
		return empList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Task saveTaskDetails(AddTaskForm taskForm) {
		Task savedTask = null;
		try {
			Task task = new Task();
			Project proj = (Project)taskDAO.getEntity(Project.class, taskForm.getProjId());
			task.setProject(proj);
			task.setTaskName(taskForm.getTaskName());
			task.setDescription(taskForm.getTaskDesc());
			task.setStartDate(TaskUtil.parseDate(taskForm.getStartDate()));
			task.setDueDate(TaskUtil.parseDate(taskForm.getDueDate()));
			
			List<String> empArr = taskForm.getEmpId();
			for(String empId: empArr){
				Employee emp = (Employee)taskDAO.getEntity(Employee.class, empId);
				if(emp!=null){
					task.getEmployeesList().add(emp);
				}
			}			
			savedTask = (Task) taskDAO.saveEntity(task);
				
		} catch (DAOException daoEx) {
			log.error("Exception occured while saving task " + daoEx.getMessage());
			ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
			throw ae;

		} catch (Exception ex) {
			log.error("Exception occured while saving task " + ex.getMessage());
			ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
			throw ae;
		}
		return savedTask;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Persistable find(Class entityobj, int key){		
		Persistable obj;
		try {
			obj = taskDAO.getEntity(entityobj, key);
			
		} catch (DAOException daoEx) {
			log.error("Exception occured while finding entity " + daoEx.getMessage());
			ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
			throw ae;

		} catch (Exception ex) {
			log.error("Exception occured while finding entity " + ex.getMessage());
			ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
			throw ae;
		}	
		return obj;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Persistable find(Class entityobj, String key) throws ApplicationException {
		Persistable obj;
		try {
			obj = taskDAO.getEntity(entityobj, key);
			
		}catch (DAOException daoEx) {
			log.error("Exception occured while finding entity " + daoEx.getMessage());
			ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
			throw ae;

		} catch (Exception ex) {
			log.error("Exception occured while finding entity " + ex.getMessage());
			ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
			throw ae;
		}		
		return obj;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectDTO> getAllProjects(String projName) {
		List<Persistable> allProjList = null;
		List<ProjectDTO> allProjDTOList = null;
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("projName", "%"+projName+"%");
			
			allProjList = taskDAO.findRecords(NamedQueryConstants.PROJECTS_BY_LIKE_NAME, params);	
			allProjDTOList = ProjectMapper._toDTOList(allProjList);
			
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting projects " + daoEx.getMessage());
			ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
			throw ae;

		} catch (Exception ex) {
			log.error("Exception occured while getting projects " + ex.getMessage());
			ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
			throw ae;
		}
		return allProjDTOList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectDTO> getAllProjects() {
		List<Persistable> allProjList = null;
		List<ProjectDTO> allProjDTOList = null;
		try {
			allProjList = taskDAO.findRecords(NamedQueryConstants.ALL_PROJECTS, null);
			allProjDTOList = ProjectMapper._toDTOList(allProjList);
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting projects " + daoEx.getMessage());
			ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
			throw ae;

		} catch (Exception ex) {
			log.error("Exception occured while getting projects " + ex.getMessage());
			ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
			throw ae;
		}
		return allProjDTOList;
	
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<TaskDTO> getAllTasks(Integer projId) throws ApplicationException {
		List<Persistable> allTasksList = null;
		List<TaskDTO> allTasksDTOList = null;
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("projId", projId);
			
			allTasksList = taskDAO.findRecords(NamedQueryConstants.ALL_TASKS_FOR_PROJ, params);
			allTasksDTOList=TaskMapper._toDTOList(allTasksList);
			
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting tasks " + daoEx.getMessage());
			ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
			throw ae;

		} catch (Exception ex) {
			log.error("Exception occured while getting tasks " + ex.getMessage());
			ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
			throw ae;
		}
		return allTasksDTOList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUsersLoginStatus() throws ApplicationException {
		try {			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("status_n", ApplicationConstants.NO);
			params.put("status_y", ApplicationConstants.YES);
			taskDAO.updateRecords(NamedQueryConstants.UPDATE_USERS_LOGIN_STATUS, params);	
		} catch (DAOException daoEx) {
			log.error("Exception occured while getting employees " + daoEx.getMessage());
			ApplicationException ae = new ApplicationException(ApplicationConstants.ERROR_MESSAGE, daoEx);
			throw ae;

		} catch (Exception ex) {
			log.error("Exception occured while getting employees " + ex.getMessage());
			ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, ex);
			throw ae;
		}		
	}
}
