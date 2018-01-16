package com.mindtree.task.service;

import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.util.ReturnStatus;

public interface LoginService {
	
	public ReturnStatus login(String userName, String password) throws ApplicationException;
	
	public void updateUser(Persistable user) throws ApplicationException;
	
	public ReturnStatus loginAs(String userName) throws ApplicationException;
	
}
