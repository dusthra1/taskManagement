package com.mindtree.task.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

	public UserDetails loadUserByUsernameandPasskey(String username, String password);
}
