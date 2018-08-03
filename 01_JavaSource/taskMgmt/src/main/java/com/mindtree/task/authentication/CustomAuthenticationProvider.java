package com.mindtree.task.authentication;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.mindtree.task.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger log = Logger.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private UserService userService;
	
	 public CustomAuthenticationProvider() {
	        super();
	    }

	@Override
	public Authentication authenticate(final Authentication authentication) {
		
		final String username = authentication.getName().trim();
	    final String password = authentication.getCredentials().toString();
	    //Spring security userDetails obj
	    UserDetailsImpl userDetails = null;
	    Authentication auth = null;
	     
		 try{
			 
			userDetails = (UserDetailsImpl) userService.loadUserByUsernameandPasskey(username, password);
			if(userDetails !=null){
				auth = new UsernamePasswordAuthenticationToken(userDetails.getPrincipalUser(), password, userDetails.getAuthorities());
			}else{
				 log.error("--ALERT-- ERROR Occurred: User "+username+" not found");
			}
			 
		 }catch(UsernameNotFoundException ex){
			 log.error("--ALERT-- ERROR Occurred: User "+username+" not found "+ex);
		 }   
		 return auth; 
	}

	@Override
	public boolean supports(final Class authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
