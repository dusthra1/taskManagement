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

import com.mindtree.task.authentication.UserDetailsImpl;
import com.mindtree.task.constants.NamedQueryConstants;
import com.mindtree.task.dao.TaskDAO;
import com.mindtree.task.model.Role;
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
		UserDetailsImpl userDetails = null;
		final List<GrantedAuthority> authorities = new ArrayList<>();

		if (null != username && null !=password) {

			Map<String, Object> queryParams = new HashMap<>();
			 queryParams.put("userName", username);
			 queryParams.put("password",password);
			 user = (User)taskDAO.findRecord(NamedQueryConstants.FIND_USER_BY_USERNAME_KEY, queryParams);			
			
			if(user != null){
				//populate permissions for User Roles
				List<Role> rolesList = user.getRoles();
				for(Role role: rolesList){
					List<TypeValues> permissionList = role.getPermissions();
					if(!permissionList.isEmpty()){
						for(TypeValues permission: permissionList){
							authorities.add(new SimpleGrantedAuthority(permission.getTypeValue()));
						}
					}
				}
				//return Spring security user
				userDetails =  new UserDetailsImpl(username, password, authorities);
				userDetails.setPrincipalUser(user);
			}
		}
		return userDetails;
	}
}
