package com.mindtree.task.authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.dao.TaskDAO;
import com.mindtree.task.model.Role;
import com.mindtree.task.model.User;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger log = Logger.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private TaskDAO  taskDAO; 
	
	 public CustomAuthenticationProvider() {
	        super();
	    }
	 
	 public Authentication doLogin(String username, String password){
		 com.mindtree.task.authentication.UserDetails userDetails =  new com.mindtree.task.authentication.UserDetails(username,password);
		 
		 return authenticate(userDetails);
	 }

	@Override
	public Authentication authenticate(final Authentication authentication) {
		
		final String username = authentication.getName();
	    final String password = authentication.getCredentials().toString();
	     
	     User userObj = null;
		 Map<String, Object> queryParams = null;
	     
	    
	    	 queryParams = new HashMap<>();
			 queryParams.put("userName", username);
			 queryParams.put("password",password);
			 userObj = (User)taskDAO.findRecord(NamedQueryConstants.FIND_USER_BY_USERNAME_KEY, queryParams);
			 
			 if(userObj !=null){
				 
				//populate User Roles
				final List<GrantedAuthority> grantedAuths = new ArrayList<>();
				List<Role> rolesList = userObj.getRoles();
				for(Role role: rolesList){
					grantedAuths.add(new SimpleGrantedAuthority(role.getRoleName()));
				}
				
				final UserDetails principal = new org.springframework.security.core.userdetails.User(username, password, grantedAuths);
				final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
				return auth; 
				
			 }else{
				 log.error("User Not found: "+username);
				 return null;
			 }
	    	      
	}

	@Override
	public boolean supports(final Class authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
