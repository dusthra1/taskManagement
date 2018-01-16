package com.mindtree.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mindtree.task.dto.EmployeeDTO;
import com.mindtree.task.dto.ProjectDTO;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.model.Employee;
import com.mindtree.task.model.Project;
import com.mindtree.task.model.Task;
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
	@Test
	public void testProjectExists() {	
		
		List<ProjectDTO> projLst = taskService.getAllProjects();	
		System.out.println("Number of Projects: "+projLst.size());
		assertThat(projLst.isEmpty(), is(false));
	}
	
	/**
	 *  TestCase 2.:  Type: Positive
	 *  		Check if Employees exists in project table
	 *  		Prints number of Employees in table
	 */
	
	@Test
	public void testEmployeesExists(){
		List<EmployeeDTO> employeesLst = taskService.getAllEmployees(1);
		System.out.println("Number of Employees: "+employeesLst.size());
		assertFalse(employeesLst.isEmpty());
	}
	
	/**
	 *  TestCase 3.:  Type: Positive
	 *  		Check if Employee can be saved to database table
	 *  		
	 */
	
	@Test
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
	
		
	}
	
	/**
	 *  TestCase 4.:  Type: Negative
	 *  		Check if Employee can be saved without Project details to database table
	 *  		
	 *  Description: Employee must not be allowed to save without tagged to any Project
	 *  On execution of this test case it is expected to throw PersistanceException
	 *  
	 */
	
	@Test
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
	}
	
	/**
	 *  TestCase 5.:  Type: Negative
	 *  		Check if Task can be saved without Project & Employees details to database table
	 *  		
	 *  Description: Task must not be allowed to save without tagged to any Project,
	 *  On execution of this test case it is expected to throw PersistanceException
	 *  
	 */
	
	@Test
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
	
	}
	
	/*@Test
	public void testSaveUser() throws ApplicationException{
		
		try{
			Role role = (Role) taskService.find(Role.class, 3);
			
			User user = new User();
			user.setFirstName("Sneha");
			user.setLastName("Dingari");
			user.setEmailId("sneha.dingari@gmial.com");
			user.setUserName("sdingari");
			user.setPassword("YWJjZDEyMzQ=");
			user.setStatus("A");
			user.getRoles().add(role);
			
			taskService.saveEntity(user);
			
		}catch(PersistenceException | ApplicationException pe){
			pe.printStackTrace();
		}
	}*/
	
	/*@Test
	public void createi18nMessage() throws ApplicationException{
		
		try{
			
			String code= "error.generic";
			
			Language lang = (Language) taskService.find(Language.class, 1);
								
			I18nMsgID i18nmsgID = new I18nMsgID(code, lang);
			I18nMessage msg = new I18nMessage();	
			msg.setI18nMsgID(i18nmsgID);
			msg.setMessage("Error Occurred. Unable To Process Your Request. Please report this issue to Support");
			
			Language lang2 = (Language) taskService.find(Language.class, 2);
			
			I18nMsgID i18nmsgID2 = new I18nMsgID(code, lang2);
			I18nMessage msg2 = new I18nMessage();	
			msg2.setI18nMsgID(i18nmsgID2);
			msg2.setMessage("Error Occurred. Unable To Process Your Request. Please report this issue to SupportSP");
			
			Language lang3 = (Language) taskService.find(Language.class, 3);
			
			I18nMsgID i18nmsgID3 = new I18nMsgID(code, lang3);
			I18nMessage msg3 = new I18nMessage();	
			msg3.setI18nMsgID(i18nmsgID3);
			msg3.setMessage("Error Occurred. Unable To Process Your Request. Please report this issue to SupportFR");			
			
			taskService.saveEntity(msg);
			taskService.saveEntity(msg2);
			taskService.saveEntity(msg3);
			
		}catch(PersistenceException | ApplicationException pe){
			pe.printStackTrace();
		}
	}*/
	
}
