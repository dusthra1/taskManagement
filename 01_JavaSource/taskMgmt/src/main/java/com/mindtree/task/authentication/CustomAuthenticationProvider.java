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
import com.mindtree.task.constants.QueryConstants;
import com.mindtree.task.dao.TaskDAO;
import com.mindtree.task.model.TypeValues;
import com.mindtree.task.model.User;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger log = Logger.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private TaskDAO  taskDAO; 
	
	 public CustomAuthenticationProvider() {
	        super();
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
				 
				//populate permissions for User Roles
				final List<GrantedAuthority> grantedAuths = new ArrayList<>();
				List<TypeValues> rolesList = userObj.getRoles();
				for(TypeValues role: rolesList){
					grantedAuths.addAll(getGrantedAuthorities(role.getTypeValueId()));
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
	
	private List<GrantedAuthority> getGrantedAuthorities(Integer roleId){
		List<GrantedAuthority> permissions = new ArrayList<>();
		
		List<Object[]> typeValues = null;
		Map<String, Object> queryParams = null;
		 
		queryParams = new HashMap<>();
		queryParams.put("typeCode","permission");
		queryParams.put("roleId", roleId);
		typeValues = taskDAO.findRecordsNSQL(QueryConstants.ROLE_PERMISSIONS, queryParams);
		
		if(!typeValues.isEmpty()){
			for(Object[] obj: typeValues){
				permissions.add(new SimpleGrantedAuthority(obj[1].toString()));
			}
		}
		return permissions;
	}
}
