package com.mindtree.task.authentication;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * UserDetails is a custom user object used by CustomAuthenticationProvider
 *
 */
public class UserDetails implements Authentication {
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
	public UserDetails(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	@Override
	public String getName() {
		return userName;
	}
	
	@Override
	public Object getCredentials() {		
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Not used
		return null;
	}

	

	@Override
	public Object getDetails() {
		// Not Used
		return null;
	}

	@Override
	public Object getPrincipal() {
		//Not Used
		return null;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public boolean isAuthenticated() {
		// Not Used
		return false;
	}

	@Override
	public void setAuthenticated(boolean arg0) {
		// Not Used
		
	}

}
