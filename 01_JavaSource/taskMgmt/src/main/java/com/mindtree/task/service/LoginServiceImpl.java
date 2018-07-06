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
import com.mindtree.task.authentication.UserDetails;
import com.mindtree.task.constants.ApplicationConstants;
import com.mindtree.task.constants.MessageCode;
import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.dao.TaskDAO;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.exception.DAOException;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.model.User;
import com.mindtree.task.util.ReturnStatus;

@Service
public class LoginServiceImpl implements LoginService  {
	
	private static final Logger log = Logger.getLogger(LoginServiceImpl.class);
	
	@Autowired
	private TaskDAO  taskDAO;
	
	@Autowired
	private CustomAuthenticationProvider authProvider;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ReturnStatus login(String userName, String password) {
		
		User userObj = null;
		Map<String, Object> queryParams = null;
		ReturnStatus returnStatus = new ReturnStatus();
	
		//Custom Authentication
		UserDetails userDetails =  new UserDetails(userName,password);
		Authentication authenticatedUser = authProvider.authenticate(userDetails);
		
		if(null != authenticatedUser && authenticatedUser.isAuthenticated()){			
			
			//Fetching user for status
			queryParams = new HashMap<>();
			queryParams.put("userName", userName);
			queryParams.put("password",password);
			userObj = (User)taskDAO.findRecord(NamedQueryConstants.FIND_USER_BY_USERNAME_KEY, queryParams);	
			/*Check if user is active in any other session*/
			/*if(ApplicationConstants.YES.equals(userObj.getLoginStatus())){
				returnStatus.setStatus(MessageCode.ERROR);
				returnStatus.setErrorCode(MessageCode.RESTRICTED_LOGIN_ACTIVE_SESSION);
			}else{*/
				returnStatus.setReturnObject(userObj);
				returnStatus.setStatus(MessageCode.SUCCESS);
				//Setting the user Object in Security context
				SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
			//}
		}else{
			returnStatus.setStatus(MessageCode.ERROR);
			returnStatus.setErrorCode(MessageCode.INVALID_LOGIN);
		}
		
		return returnStatus;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUser(Persistable obj) {
		if(obj !=null){
			try {
				taskDAO.saveEntity(obj);
				
			} catch (DAOException daoEx) {
				log.error("Exception occured while update entity " + daoEx.getMessage());
				throw new ApplicationException(MessageCode.GENERIC_ERROR, daoEx);

			} catch (Exception ex) {
				log.error("Exception occured while update entity " + ex.getMessage());
				throw new ApplicationException(MessageCode.GENERIC_ERROR, ex);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ReturnStatus loginAs(String userName) {
		
		User userObj = null;
		Map<String, Object> queryParams = null;
		ReturnStatus returnStatus = new ReturnStatus();
		
	try{
			queryParams = new HashMap<>();
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
