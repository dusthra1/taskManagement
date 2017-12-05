package com.mindtree.task.service;

import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.util.Status;

public interface LoginService {
	
	public Status login(String userName, String password) throws ApplicationException;
	
	public void updateUser(Persistable user) throws ApplicationException;
	
	public Status loginAs(String userName) throws ApplicationException;
	
}
