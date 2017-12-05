package com.mindtree.task.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.task.authentication.CustomAuthenticationProvider;
import com.mindtree.task.constants.ApplicationConstants;
import com.mindtree.task.constants.MessageCode;
import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.dao.TaskDAO;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.exception.DAOException;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.model.User;
import com.mindtree.task.util.Status;

@Service
public class LoginServiceImpl implements LoginService  {
	
	private static final Logger log = Logger.getLogger(LoginServiceImpl.class);
	
	@Autowired
	private TaskDAO  taskDAO;
	
	@Autowired
	private CustomAuthenticationProvider authProvider;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Status login(String userName, String password) throws ApplicationException {
		
		User userObj = null;
		Map<String, Object> queryParams = null;
		Status returnStatus = new Status();
	
		//Custom Authentication
		Authentication authenticatedUser = authProvider.doLogin(userName, password);
		
		if(null != authenticatedUser && authenticatedUser.isAuthenticated()){
			//Setting the user Object in Security context
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
			
			//Fetching user for status
			queryParams = new HashMap<String, Object>();
			queryParams.put("userName", userName);
			queryParams.put("password",password);
			userObj = (User)taskDAO.findRecord(NamedQueryConstants.FIND_USER_BY_USERNAME_PWD, queryParams);		
			
			returnStatus.setReturnObject(userObj);
			returnStatus.setStatus(MessageCode.SUCCESS);
		}else{
			returnStatus.setStatus(MessageCode.ERROR);
			returnStatus.setErrorCode(MessageCode.INVALID_LOGIN);
		}
		
		return returnStatus;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUser(Persistable obj) throws ApplicationException {
		if(obj !=null){
			try {
				taskDAO.updateEntity(obj);
				
			} catch (DAOException daoEx) {
				log.error("Exception occured while update entity " + daoEx.getMessage());
				ApplicationException ae = new ApplicationException(MessageCode.GENERIC_ERROR, daoEx);
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
	public Status loginAs(String userName) throws ApplicationException {
		
		User userObj = null;
		Map<String, Object> queryParams = null;
		Status returnStatus = new Status();
		
	try{
			queryParams = new HashMap<String, Object>();
			queryParams.put("userName", userName);			
			userObj = (User)taskDAO.findRecord(NamedQueryConstants.FIND_USER_BY_USERNAME, queryParams);			
			
			if(userObj !=null){
				returnStatus.setReturnObject(userObj);
				returnStatus.setStatus(MessageCode.SUCCESS);
			} else {
				returnStatus.setStatus(MessageCode.ERROR);
				returnStatus.setErrorCode(MessageCode.INVALID_LOGIN);
			}
			
		} catch (Exception ex) {
			log.error("An error occured while authenticating user: username [" + userName + "] " + ex.getMessage());
			returnStatus.setStatus(ApplicationConstants.GENERIC_ERROR);
			returnStatus.setErrorCode(MessageCode.INVALID_LOGIN);
		}		
		
		return returnStatus;
	}

}
