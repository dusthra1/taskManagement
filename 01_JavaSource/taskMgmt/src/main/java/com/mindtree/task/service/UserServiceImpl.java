package com.mindtree.task.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.task.authentication.CustomUserDetails;
import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.constants.QueryConstants;
import com.mindtree.task.dao.TaskDAO;
import com.mindtree.task.model.TypeValues;
import com.mindtree.task.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private TaskDAO  taskDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserDetails loadUserByUsernameandPasskey(String username, String password) {
		User user = null;
		CustomUserDetails userDetails = null;
		final List<GrantedAuthority> authorities = new ArrayList<>();

		if (null != username && null !=password) {

			Map<String, Object> queryParams = new HashMap<>();
			 queryParams.put("userName", username);
			 queryParams.put("password",password);
			 user = (User)taskDAO.findRecord(NamedQueryConstants.FIND_USER_BY_USERNAME_KEY, queryParams);			
			
			if(user != null){
				//populate permissions for User Roles
				List<TypeValues> rolesList = user.getRoles();
				for(TypeValues role: rolesList){
					authorities.addAll(getGrantedAuthorities(role.getTypeValueId()));
				}
				//return Spring security user
				userDetails =  new CustomUserDetails(username, password, authorities);
			}
		}
		return userDetails;
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
