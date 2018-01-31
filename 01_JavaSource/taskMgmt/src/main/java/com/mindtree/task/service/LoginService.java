package com.mindtree.task.service;

import com.mindtree.task.model.Persistable;
import com.mindtree.task.util.ReturnStatus;

public interface LoginService {
	
	public ReturnStatus login(String userName, String password);
	
	public void updateUser(Persistable user);
	
	public ReturnStatus loginAs(String userName);
	
}
