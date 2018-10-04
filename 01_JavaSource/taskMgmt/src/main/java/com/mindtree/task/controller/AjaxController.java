package com.mindtree.task.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.mindtree.task.dto.EmployeeDTO;
import com.mindtree.task.dto.FileModelDTO;
import com.mindtree.task.dto.ProjectDTO;
import com.mindtree.task.dto.ProjectMapper;
import com.mindtree.task.dto.TaskDTO;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.service.TaskService;
import com.mindtree.task.util.CriteriaExpression;
import com.mindtree.task.util.JSONUtil;
import com.mindtree.task.util.TaskUtil;

@Controller
public class AjaxController {
	
private static final Logger log = Logger.getLogger(AjaxController.class);
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private JSONUtil jsonUtil;

	/*
	 * GET Requests
	 */
	
	@RequestMapping(method= RequestMethod.GET, value="/manageEmployees.do")
	public void manageEmployees(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("jsonstr") String jsonstr){
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		String type = jsonRequest.has("type") ? jsonRequest.getString("type"):"";	
		
		JSONObject jsonResponse = new JSONObject();
		log.debug("type=: "+type);
		
		switch(type){
		
		case "view":
			
			String filterEmpName =  jsonRequest.has("name") ? jsonRequest.getString("name") :"";
			String filterMid =  jsonRequest.has("mid") ? jsonRequest.getString("mid") :"";
			String filterEmail = jsonRequest.has("emailId") ? jsonRequest.getString("emailId") :"";
			int filterProject = jsonRequest.has("projId") ? jsonRequest.getInt("projId"):0;
			
			List<EmployeeDTO> empList = null;
			CriteriaExpression criteriaExp = null;
			List<CriteriaExpression> criteriaExpList = new ArrayList<>();
			
			if(!filterMid.isEmpty() && !"undefined".equalsIgnoreCase(filterMid)){
				criteriaExp = new CriteriaExpression("mid",filterMid,"LIKE");
				criteriaExpList.add(criteriaExp);
			}if(!filterEmpName.isEmpty() && !"undefined".equalsIgnoreCase(filterEmpName)){
				criteriaExp = new CriteriaExpression("name",filterEmpName,"LIKE");
				criteriaExpList.add(criteriaExp);
			}
			if(!filterEmail.isEmpty() && !"undefined".equalsIgnoreCase(filterEmail)){
				criteriaExp = new CriteriaExpression("emailId",filterEmail,"LIKE");
				criteriaExpList.add(criteriaExp);
			}
			if(filterProject > 0){
				ProjectDTO proj = taskService.getProject(Integer.valueOf(filterProject));
				criteriaExp = new CriteriaExpression("project",ProjectMapper.toEntity(proj),"EQ");
				criteriaExpList.add(criteriaExp);
			}
			
			empList = taskService.searchEmployee(criteriaExpList);
			
			if (!empList.isEmpty()) {
				Gson gson = new Gson();
				String 	jsonStr =gson.toJson(empList);
				jsonResponse.put("employees", jsonStr);	
				jsonResponse.put("itemCount", empList.size());
				jsonResponse.put("results", "success");
			}else{
				jsonResponse.put("results", "error");
			}
			break;
			
		case "projects":
			List<ProjectDTO> projList = new ArrayList<>();
			
			projList = taskService.searchProject(null);
			projList.add(0,new ProjectDTO(0,"--SELECT--"));
			
			if (!projList.isEmpty()) {
				Gson gson = new Gson();
				String 	jsonStr =gson.toJson(projList);
				jsonResponse.put("projects", jsonStr);	
				jsonResponse.put("itemCount", projList.size());
				jsonResponse.put("results", "success");
			}else{
				jsonResponse.put("results", "empty");
			}
			break;
		
		default:
			log.info("Manage Employees: Did not match any case");
			break;
		}
		jsonUtil.handleJSONResponse(response, jsonResponse.toString());
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/viewFiles.do")
	public void viewFiles(@RequestParam("jsonstr") String jsonstr, HttpServletRequest request, HttpServletResponse response){
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		JSONObject jsonResponse = new JSONObject();
		
		String type = jsonRequest.has("type") ? jsonRequest.getString("type"):"";
		
		switch(type){
		case "view":
			List<FileModelDTO> filesDTOList = taskService.getAllFiles();
			
			if (!filesDTOList.isEmpty()) {
				Gson gson = new Gson();
				String jsonStr=gson.toJson(filesDTOList);			
				
				jsonResponse.put("fileList", jsonStr);	
				jsonResponse.put("itemCount", filesDTOList.size());
				jsonResponse.put("results", "success");
			}else{
				jsonResponse.put("results", "empty");
			}
			break;
			
		default:
			log.info("ViewUploadedFiles: Did not match any case");
			break;
		}
		jsonUtil.handleJSONResponse(response, jsonResponse.toString());
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/manageProject.do")
	public void manageProject(@RequestParam("jsonstr") String jsonstr, HttpServletRequest request, HttpServletResponse response){
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		String type = jsonRequest.has("type") ? jsonRequest.getString("type"):"";	
		
		JSONObject jsonResponse = new JSONObject();
		log.debug("type=: "+type);
		
		
		List<ProjectDTO> allProjList = null;
		
		switch(type){
		
			case "view":				
					String filterProjName = jsonRequest.has("projName") ? jsonRequest.getString("projName"):"";
					String filterProjDesc = jsonRequest.has("projDesc") ? jsonRequest.getString("projDesc"):"";
					String sortOrder = jsonRequest.has("sortOrder") ? jsonRequest.getString("sortOrder"):"";
					//String sortField = jsonRequest.has("sortField") ? jsonRequest.getString("sortField"):"";
					
					CriteriaExpression criteriaExp = null;
					List<CriteriaExpression> criteriaExpList = new ArrayList<>();
					
					if(!filterProjName.isEmpty() && !"undefined".equalsIgnoreCase(filterProjName) ){
						criteriaExp = new CriteriaExpression("name",filterProjName,"LIKE");
						criteriaExpList.add(criteriaExp);
					}
					if(!filterProjDesc.isEmpty() && !"undefined".equalsIgnoreCase(filterProjDesc) ){
						criteriaExp = new CriteriaExpression("description",filterProjDesc,"LIKE");
						criteriaExpList.add(criteriaExp);
					}
					
					allProjList= taskService.searchProject(criteriaExpList);
					
					//Implementing Lambda expressions for sorting collections
					if("desc".equals(sortOrder)){
						Collections.sort(allProjList,(p1,p2)-> p1.getName().compareTo(p2.getName())); 
					}else{
						Collections.sort(allProjList,(p2,p1)-> p1.getName().compareTo(p2.getName())); 
					}
					
					
					if (!allProjList.isEmpty()) {
						Gson gson = new Gson();
						String jsonStr=gson.toJson(allProjList);			
						
						jsonResponse.put("projects", jsonStr);	
						jsonResponse.put("itemCount", allProjList.size());
						jsonResponse.put("results", "success");
					}else{
						jsonResponse.put("results", "empty");
					}
					jsonUtil.handleJSONResponse(response, jsonResponse.toString());
					break;
				
				default:
					log.debug("Manage Projects: Did not match any case");
					break;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getTasksHTML.do")
	public void getTaskasHTML(@RequestParam("jsonstr") String jsonstr, 
			HttpServletRequest request, HttpServletResponse response){		
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		String projId = jsonRequest.getString("projId");
		StringBuilder htmlStr = new StringBuilder();
		//Get all tasks for project
		try {
			List<TaskDTO> allTaskList= taskService.getAllTasks(Integer.valueOf(projId));
			
			if(allTaskList !=null && !allTaskList.isEmpty()){
				
				for(TaskDTO tsk : allTaskList){
					htmlStr.append("<table>")
						   .append("<tr><td></td></tr><tr><td></td></tr>")
						   .append("<tr><td>Task: <b>"+tsk.getTaskName()+"</b></td></tr>")
						   .append("<tr><td>Task Description: "+tsk.getDescription()+"</td></tr>")
						   .append("<tr><td>Start Date: "+tsk.getStartDate()+"</td></tr>")
						   .append("<tr><td>Due Date: "+tsk.getDueDate()+"</td></tr>")
						   .append("<tr><td>Employees </td></tr>")
						   .append("<tr><td><table border=\"1\">")
						   .append("<tr><td>MID</td><td>Employee Name</td></tr>");
						   for(EmployeeDTO emp: tsk.getEmployeeDTOList()){
							   htmlStr.append("<tr><td>"+emp.getMid()+"</td><td>"+emp.getName()+"</td></tr>");							   
						   }
						   htmlStr.append("</table></td></tr>")
						   		  .append("<tr><td></td></tr>");
				    htmlStr.append("</table>");					
				}
			}else{
				htmlStr.append("<table>")
				   .append("<tr><td>No Tasks Found</td></tr>")
				   .append("</table>");	
			}
			
		} catch (NumberFormatException | ApplicationException e) {
			log.error("Error Occurred --"+ e);
		}
		
		jsonUtil.handleHTMLResponse(response, htmlStr.toString());
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getProjEmployees.do")
	public void getProjectEmployees(@RequestParam("jsonstr") String jsonstr, 
						HttpServletRequest request, HttpServletResponse response){			

		List<EmployeeDTO> empDTOList = null;
		JSONObject jsonResponse = new JSONObject();
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		int projId = jsonRequest.has("projId") ? jsonRequest.getInt("projId"): 0;
		
		CriteriaExpression criteriaExp = null;
		List<CriteriaExpression> criteriaExpList = new ArrayList<>();
		
		if(projId > 0){
			ProjectDTO proj = taskService.getProject(projId);
			criteriaExp = new CriteriaExpression("project",ProjectMapper.toEntity(proj),"EQ");
			criteriaExpList.add(criteriaExp);
			
			empDTOList = taskService.searchEmployee(criteriaExpList);
		}
		
		if (null != empDTOList && !empDTOList.isEmpty()) {
			Gson gson = new Gson();
			String jsonStr=gson.toJson(empDTOList);
			
			jsonResponse.put("EMP_LIST", jsonStr);
			jsonResponse.put("result", "success");
			
		} else {
			jsonResponse.put("result", "empty");
			
		}
		jsonUtil.handleJSONResponse(response, jsonResponse.toString());
	}
	
	/*
	 * POST Requests
	 */
	
	@RequestMapping(method = RequestMethod.POST, value ="/addEmployeeAjx.do")
	public void addEmp(@RequestParam("jsonstr") String jsonstr, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		String type = jsonRequest.has("type") ? jsonRequest.getString("type"):"";
		String mid = jsonRequest.has("mid") ? jsonRequest.getString("mid"):"";
		
		JSONObject jsonResponse = new JSONObject();
		
		switch(type){
		
		case "add":
			String name = jsonRequest.has("name") ? jsonRequest.getString("name") :"";
			String joinDate = jsonRequest.has("joinDate") ? jsonRequest.getString("joinDate"):"";
			String email = jsonRequest.has("emailId") ? jsonRequest.getString("emailId") :"";
			int projId = jsonRequest.has("projId") ? jsonRequest.getInt("projId"):0;
			
			EmployeeDTO emp=  new EmployeeDTO();
			emp.setMid(mid);
			emp.setName(name);
			emp.setJoinDate(TaskUtil.parseDate(joinDate));
			emp.setEmailId(email);
			ProjectDTO prj = taskService.getProject(projId);
			emp.setProject(prj);
			taskService.saveEmployee(emp);
			jsonResponse.put("results", "success");
			break;
		
		case "update":
			String upname = jsonRequest.has("name") ? jsonRequest.getString("name") :"";
			String upjoinDate = jsonRequest.has("joinDate") ? jsonRequest.getString("joinDate"):"";
			String upemail = jsonRequest.has("emailId") ? jsonRequest.getString("emailId") :"";
			int upprojId = jsonRequest.has("projId") ? jsonRequest.getInt("projId"):0;
			
			CriteriaExpression criteriaExp = null;
			List<CriteriaExpression> criteriaExpList = new ArrayList<>();
			EmployeeDTO updateEmp = null;
			
			if(null != mid && !mid.isEmpty()){
				criteriaExp = new CriteriaExpression("mid",mid,"EQ");
				criteriaExpList.add(criteriaExp);
				List<EmployeeDTO> empList = taskService.searchEmployee(criteriaExpList);
				if(!empList.isEmpty()){
					updateEmp = empList.get(0);
				}
			}
			
			if(updateEmp !=null)	{
				updateEmp.setMid(mid);
				updateEmp.setName(upname);
				updateEmp.setJoinDate(TaskUtil.parseDate(upjoinDate));
				updateEmp.setEmailId(upemail);
				ProjectDTO upprj = taskService.getProject(upprojId);
				updateEmp.setProject(upprj);
				taskService.saveEmployee(updateEmp);
				jsonResponse.put("results", "success");
			}
			break;
		case "delete":
			EmployeeDTO empToDel=null;
			criteriaExpList = new ArrayList<>();
			try {
				
				if(null != mid && !mid.isEmpty()){
					criteriaExp = new CriteriaExpression("mid",mid,"EQ");
					criteriaExpList.add(criteriaExp);
					List<EmployeeDTO> empList = taskService.searchEmployee(criteriaExpList);
					if(!empList.isEmpty()){
						empToDel = empList.get(0);
					}
				}
				
				if(empToDel !=null){
					taskService.deleteEmployee(empToDel);
					jsonResponse.put("results", "success");
				}
			} catch (NumberFormatException | ApplicationException e) {
				log.error("Error Occurred --"+ e);
			}			
			break;
		
		default:
			log.info("Add/Update Projects: Did not match any case");
			break;
		}
		
		jsonUtil.handleJSONResponse(response, jsonResponse.toString());
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/addProject.do")
	public void addProject(@RequestParam("jsonstr") String jsonstr, HttpServletRequest request, HttpServletResponse response){
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		JSONObject jsonResponse = new JSONObject();
		
		String type = jsonRequest.has("type") ? jsonRequest.getString("type"):"";
		
		String id = null;
		String name = null;
		String desc = null;	
		
		switch(type){
		
		case "add":
			name = jsonRequest.has("name") ? jsonRequest.getString("name") :"";
			desc = jsonRequest.has("desc") ? jsonRequest.getString("desc") :"";
			ProjectDTO projDTO=  new ProjectDTO();
			projDTO.setName(name);
			projDTO.setDescription(desc);
			taskService.saveProject(projDTO);
			jsonResponse.put("results", "success");
			break;
		
		case "update":
			id = jsonRequest.has("id") ? jsonRequest.getString("id"):"";
			name = jsonRequest.has("name") ? jsonRequest.getString("name") :"";
			desc = jsonRequest.has("desc") ? jsonRequest.getString("desc") :"";
			ProjectDTO updateproj =  taskService.getProject(Integer.valueOf(id));
			if(updateproj !=null)	{
				updateproj.setId(Integer.valueOf(id));
				updateproj.setName(name);
				updateproj.setDescription(desc);
				taskService.saveProject(updateproj);
				jsonResponse.put("results", "success");
			}
			break;
			
		case "delete":
			id = jsonRequest.has("id") ? jsonRequest.getString("id"):"";
			ProjectDTO prj;
			try {
				prj = taskService.getProject(Integer.valueOf(id));
				if(prj !=null){
					taskService.deleteProject(prj);
					jsonResponse.put("results", "success");
				}
			} catch (NumberFormatException | ApplicationException e) {
				log.error("Error Occurred --"+ e);
			}			
			break;
		
		default:
			log.info("Add/Update Projects: Did not match any case");
			break;
		}
		jsonUtil.handleJSONResponse(response, jsonResponse.toString());
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/deleteFile.do")
	public void deleteFile(@RequestParam("jsonstr") String jsonstr, HttpServletRequest request, HttpServletResponse response){
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		JSONObject jsonResponse = new JSONObject();
		
		String id = jsonRequest.has("id") ? jsonRequest.getString("id"):"";
		FileModelDTO fileDTO;
		try {
			fileDTO = taskService.getFile(Integer.valueOf(id));
			if(fileDTO !=null){
				taskService.deleteFile(fileDTO);
				jsonResponse.put("results", "success");
			}
		} catch (NumberFormatException | ApplicationException e) {
			log.error("Error Occurred --"+ e);
		}			
		
		jsonUtil.handleJSONResponse(response, jsonResponse.toString());
	}
}
