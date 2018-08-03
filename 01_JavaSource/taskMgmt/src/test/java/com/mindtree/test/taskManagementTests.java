package com.mindtree.test;

import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mindtree.task.constants.Locale;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.model.I18nMessage;
import com.mindtree.task.model.I18nMsgID;
import com.mindtree.task.model.Role;
import com.mindtree.task.model.TypeValues;
import com.mindtree.task.model.User;
import com.mindtree.task.service.TaskService;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations={"classpath:dispatcher-junit-servlet.xml"})

public class taskManagementTests extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private TaskService taskService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("---------TaskManagement Test Initiated-------------------");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("---------TaskManagement Test Ended-------------------");
	}

	/**
	 *  TestCase 1.:  Type: Positive
	 *  		Check if Projects exists in project table
	 *  		Prints number of Projects in table
	 */
	/*@Test
	public void testProjectExists() {	
		
		List<ProjectDTO> projLst = taskService.getAllProjects();	
		System.out.println("Number of Projects: "+projLst.size());
		assertThat(projLst.isEmpty(), is(false));
	}*/
	
	/**
	 *  TestCase 2.:  Type: Positive
	 *  		Check if Employees exists in project table
	 *  		Prints number of Employees in table
	 */
	
	/*@Test
	public void testEmployeesExists(){
		List<EmployeeDTO> employeesLst = taskService.getAllEmployees(1);
		System.out.println("Number of Employees: "+employeesLst.size());
		assertFalse(employeesLst.isEmpty());
	}*/
	
	/**
	 *  TestCase 3.:  Type: Positive
	 *  		Check if Employee can be saved to database table
	 *  		
	 */
	
	/*@Test
	public void testEmpPersist() throws ApplicationException{
		
		try{
			List<EmployeeDTO> employeesLstBefore = taskService.getAllEmployees(1);
			int countBefore = employeesLstBefore.size();
			
			String randomNum = String.valueOf(Math.random());
			String mid = "MJunit"+randomNum.substring(3, 6);
			Employee mockEmp = new Employee();
			mockEmp.setMid(mid);
			mockEmp.setName("JunitName");
			mockEmp.setEmailId("JunitName@email.com");
			mockEmp.setJoinDate(new Date());
			Project proj = (Project)taskService.find(Project.class, 1);
			mockEmp.setProject(proj);		
			taskService.saveEntity(mockEmp);
			
			List<EmployeeDTO> employeesLstAfter = taskService.getAllEmployees(1);
			int countAfter = employeesLstAfter.size();
					
			assertTrue(countAfter == countBefore+1);
			
		}catch(PersistenceException | ApplicationException pe){
			pe.printStackTrace();
		}
	
		
	}*/
	
	/**
	 *  TestCase 4.:  Type: Negative
	 *  		Check if Employee can be saved without Project details to database table
	 *  		
	 *  Description: Employee must not be allowed to save without tagged to any Project
	 *  On execution of this test case it is expected to throw PersistanceException
	 *  
	 */
	
	/*@Test
	public void  testEmpPersistWithoutProj() throws ApplicationException{
		
		try{
			String randomNum = String.valueOf(Math.random());
			String mid = "MJunit"+randomNum.substring(3, 6);
			Employee mockEmp = new Employee();
			mockEmp.setMid(mid);
			mockEmp.setName("JunitName");
			mockEmp.setEmailId("JunitName@email.com");
			mockEmp.setJoinDate(new Date());
			taskService.saveEntity(mockEmp);
			
		}catch(PersistenceException pe){
			assertTrue(pe instanceof PersistenceException);
		}
	}*/
	
	/**
	 *  TestCase 5.:  Type: Negative
	 *  		Check if Task can be saved without Project & Employees details to database table
	 *  		
	 *  Description: Task must not be allowed to save without tagged to any Project,
	 *  On execution of this test case it is expected to throw PersistanceException
	 *  
	 */
	
	/*@Test
	public void testTaskPersistWithoutProj() throws ApplicationException{
		try{
			Task task = new Task();
			task.setTaskName("Junit Test Cases");
			task.setDescription("Junit Test cases execution");
			task.setStartDate(new Date());
			task.setDueDate(new Date());
			
			taskService.saveEntity(task);
			
		}catch(PersistenceException pe){
			assertTrue(pe instanceof PersistenceException);
		}
	
	}*/
	
	/*@Test
	public void testSaveUser() throws ApplicationException{
		
		try{
			
			Role role = (Role) taskService.find(Role.class, 120);
			
			User user = new User();
			user.setFirstName("Sneha");
			user.setLastName("Dingari");
			user.setEmailId("sdingari@gmial.com");
			user.setUserName("sdingari");
			user.setPassword("YWJjZDEyMzQ=");
			user.setStatus("A");
			user.getRoles().add(role);
			
			taskService.saveEntity(user);
			
		}catch(PersistenceException | ApplicationException pe){
			pe.printStackTrace();
		}
	}*/
	
	@Test
	public void createi18nMessage() throws ApplicationException{
		
		try{
			
			String code= "session.timeout";
			
			TypeValues locale = (TypeValues) taskService.find(TypeValues.class, Locale.ENGLISH.getValue());
								
			I18nMsgID i18nmsgID = new I18nMsgID(code, locale);
			I18nMessage msg = new I18nMessage();	
			msg.setI18nMsgID(i18nmsgID);
			msg.setMessage("Your session timed out. Please login again");
			
			TypeValues locale2 = (TypeValues) taskService.find(TypeValues.class, Locale.SPANISH.getValue());
			
			I18nMsgID i18nmsgID2 = new I18nMsgID(code, locale2);
			I18nMessage msg2 = new I18nMessage();	
			msg2.setI18nMsgID(i18nmsgID2);
			msg2.setMessage("Your session timed out. Please login againSP");
			
			TypeValues locale3 = (TypeValues) taskService.find(TypeValues.class, Locale.FRENCH.getValue());
			
			I18nMsgID i18nmsgID3 = new I18nMsgID(code, locale3);
			I18nMessage msg3 = new I18nMessage();	
			msg3.setI18nMsgID(i18nmsgID3);
			msg3.setMessage("Your session timed out. Please login againFR");			
		
			taskService.saveEntity(msg);
			taskService.saveEntity(msg2);
			taskService.saveEntity(msg3);
			
		}catch(PersistenceException | ApplicationException pe){
			pe.printStackTrace();
		}
	}
	
/*	@Test
	public void testSaveTypeCodeValue() throws ApplicationException{
		
		try{
			TypeCode typeCode = (TypeCode) taskService.find(TypeCode.class, 3);
			TypeValues typeVal1 = new TypeValues();		
			typeVal1.setTypeCode(typeCode);
			typeVal1.setTypeValue("PERM_ACCESS_VIEW_TASKS");
			typeVal1.setDescription("manager access");
			
			taskService.saveEntity(typeVal1);
			
		}catch(PersistenceException | ApplicationException pe){
			pe.printStackTrace();
		}
	}*/
	
	/*@Test
	public void testSaveRolePermission() throws ApplicationException{
		
		try{
			Map<String,Object> params = new HashMap<>();
			params.put("roleId", Role.MANAGER.getValue());
			params.put("permissionId", RolePermission.PERM_ACCESS_VIEW_TASKS.getValue());
			taskService.saveRecord(QueryConstants.INSERT_ROLE_PERMISSIONS, params);
			
		}catch(PersistenceException | ApplicationException pe){
			pe.printStackTrace();
		}
	}*/
	
}
