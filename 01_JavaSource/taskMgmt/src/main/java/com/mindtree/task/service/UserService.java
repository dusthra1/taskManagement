package com.mindtree.task.service;

import java.util.ArrayList;
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

import com.mindtree.task.authentication.CustomUserDetails;
import com.mindtree.task.constants.QueryConstants;
import com.mindtree.task.dao.TaskDAO;
import com.mindtree.task.model.TypeValues;
import com.mindtree.task.model.User;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private TaskDAO  taskDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserDetails loadUserByUsername(final String userName) {
		User user = null;

		if (null != userName) {
			Map<String, Object> param = new HashMap<>();
			param.put("userId", userName);
			user = (User) taskDAO.findRecord(QueryConstants.FIND_USER_BY_USERNAME, param);
			
			//additional information on security object
			String password = user.getPassword();
			boolean enabled =true;
			boolean accountNonExpired= true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			//populate permissions for User Roles
			final List<GrantedAuthority> authorities = new ArrayList<>();
			List<TypeValues> rolesList = user.getRoles();
			for(TypeValues role: rolesList){
				authorities.addAll(getGrantedAuthorities(role.getTypeValueId()));
			}
			
			//Create Spring security user
			return new CustomUserDetails(userName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
			
		}else{
			throw new UsernameNotFoundException("User not Found..");
		}
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
