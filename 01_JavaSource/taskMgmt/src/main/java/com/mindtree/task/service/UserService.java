package com.mindtree.task.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.task.constants.QueryConstants;
import com.mindtree.task.dao.TaskDAO;
import com.mindtree.task.model.Role;
import com.mindtree.task.model.User;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private TaskDAO  taskDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
		org.springframework.security.core.userdetails.User securityUser = null;
		User user = null;

		if (null != userName) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userName);
			user = (User) taskDAO.findRecord(QueryConstants.FIND_USER_BY_USERNAME, param);
			
			//additional information on security object
			String password = user.getPassword();
			boolean enabled =true;
			boolean accountNonExpired= true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			//populate User Roles
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			List<Role> rolesList = user.getRoles();
			for(Role role: rolesList){
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			}
			
			//Create Spring security user
			securityUser = new org.springframework.security.core.userdetails.User(userName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
			
		}else{
			throw new UsernameNotFoundException("User not Found..");
		}
		return securityUser;		
	}

}
