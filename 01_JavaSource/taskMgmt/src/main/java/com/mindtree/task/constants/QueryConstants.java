package com.mindtree.task.constants;

public final class QueryConstants {	
	
	//public static final String ALL_PROJECTS = "from Project";
	
	//public static final String PROJECTS_BY_LIKE_NAME = "from Project where name like :projName order by name asc"; 
	
	//public static final String PROJECT_EMPLOYEES = "from Employee emp where emp.project.id= :projId";
	
	//public static final String ALL_EMPLOYEES = "from Employee";
	
	public static final String ALL_TASKS_FOR_PROJ = "from Task where project.id=:projId";	
	
	public static final String FIND_USER_BY_USERNAME_KEY = "from User where userName= :userName and password= :password and status='A'";
	
	public static final String FIND_USER_BY_USERNAME = "from User where userName= :userName and status='A'";
	
	public static final String I18N_MESSAGES = "from I18nMessage";
	
	public static final String UPDATE_USERS_LOGIN_STATUS = "update User set loginStatus= :status_n where loginStatus= :status_y";
	
	public static final String ALL_FILES = "from FileModel";
	
	public static final String INSERT_ROLE_PERMISSIONS = "insert into ROLE_PERMISSIONS (ROLE_ID,PERMISSION_ID) values(:roleId, :permissionId)";
	
	public static final String EMPLOYEES_FOR_IDS = "from Employee emp where mid in (:mIds)";

}
