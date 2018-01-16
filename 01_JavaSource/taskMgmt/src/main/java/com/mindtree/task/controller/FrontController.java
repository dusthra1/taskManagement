package com.mindtree.task.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mindtree.task.constants.ApplicationConstants;
import com.mindtree.task.dto.EmployeeDTO;
import com.mindtree.task.dto.ProjectDTO;
import com.mindtree.task.dto.TaskDTO;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.form.AddTaskForm;
import com.mindtree.task.model.Employee;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.model.Project;
import com.mindtree.task.model.Task;
import com.mindtree.task.reports.ExcelReportView;
import com.mindtree.task.service.TaskService;
import com.mindtree.task.util.JSONUtil;
import com.mindtree.task.util.TaskUtil;
import com.mindtree.task.validators.TaskValidator;


@Controller
public class FrontController {
	
	private static final Logger log = Logger.getLogger(FrontController.class);
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(method = RequestMethod.GET, value ="/home.do")
	public ModelAndView handleRequest(HttpServletRequest request) 
	{	
		return new ModelAndView("homePage");
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value ="/assignTask.do")
	public ModelAndView createTask(HttpServletRequest request, Model model)
	{
		
		AddTaskForm addTaskForm = new AddTaskForm();
		request.setAttribute("addTaskFormDetails", addTaskForm);
		
		List<ProjectDTO> allProjList= taskService.getAllProjects();		
		request.setAttribute("allProjList", allProjList);
		ModelAndView modelview=new ModelAndView();
		modelview.addObject(model);
		modelview.setViewName(ApplicationConstants.ADD_TASK_PAGE);
		return modelview;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/addTask.do")
	public ModelAndView saveTaskDetails(HttpServletRequest request,
										HttpServletResponse response,@ModelAttribute("addTaskFormDetails") AddTaskForm addTaskForm, 
										BindingResult result){
		ModelAndView modelview= new ModelAndView();
		TaskValidator taskValidator=new TaskValidator();
		taskValidator.validate(addTaskForm, result);
		
		if(result.hasErrors()){
			List<ProjectDTO> allProjList= taskService.getAllProjects();		
			request.setAttribute("allProjList", allProjList);	
			modelview.setViewName(ApplicationConstants.ADD_TASK_PAGE);
		}else{
			Task savedTask = taskService.saveTaskDetails(addTaskForm);
			String message="Task Saved Successfully: Id: "+savedTask.getTaskId();
			modelview.addObject("message", message);
			modelview.setViewName(ApplicationConstants.HOME_PAGE);
		}
		return modelview;
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getProjEmployees.do")
	public void getProjectEmployees(@RequestParam("jsonstr") String jsonstr, 
						HttpServletRequest request, HttpServletResponse response){			

		JSONObject jsonResponse = new JSONObject();
		JSONUtil jsonUtil = new JSONUtil();
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		String projId = jsonRequest.getString("projId");
		List<EmployeeDTO> empDTOList = taskService.getAllEmployees(Integer.valueOf(projId));
		
		if (!empDTOList.isEmpty()) {
			//Gson gson = TaskUtil.getGsonInstance();
			Gson gson = new Gson();
			String jsonStr=gson.toJson(empDTOList);
			
			jsonResponse.put("EMP_LIST", jsonStr);
			jsonResponse.put("result", "success");
			
		} else {
			jsonResponse.put("result", "empty");
			
		}
		jsonUtil.handleJSONResponse(response, jsonResponse.toString());
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@RequestMapping(method = RequestMethod.GET, value ="/viewTasks.do")
	public ModelAndView viewTasks(HttpServletRequest request)
	{
		List<ProjectDTO> allProjList= taskService.getAllProjects();		
		StringBuilder sb = new StringBuilder("[");
		int index = 0;
		for(ProjectDTO obj:allProjList){
			ProjectDTO prj = (ProjectDTO)obj;
			if(index>0){sb.append(",");}
			sb.append("'").append(prj.getName()).append(":").append(prj.getId()).append("'");
			index++;
		}
		sb.append("]");
		request.setAttribute("projectsList", sb.toString());	
		
		return new ModelAndView(ApplicationConstants.VIEW_TASK_PAGE);
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/getTasksHTML.do")
	public void getTaskasHTML(@RequestParam("jsonstr") String jsonstr, 
			HttpServletRequest request, HttpServletResponse response){		
		
		JSONUtil jsonUtil = new JSONUtil();
		JSONObject jsonRequest = new JSONObject(jsonstr);
		String projId = jsonRequest.getString("projId");
		StringBuilder htmlStr = new StringBuilder();
		//Get all tasks for project
		try {
			List<TaskDTO> allTaskList= taskService.getAllTasks(Integer.valueOf(projId));
			
			if(allTaskList !=null && allTaskList.size()>0){
				
				for(TaskDTO tsk : allTaskList){
					//Task tsk = (Task) obj;
					htmlStr.append("<table>")
						   .append("<tr><td></td></tr>")
						   .append("<tr><td></td></tr>")
						   .append("<tr><td>Task: <b>"+tsk.getTaskName()+"</b></td></tr>")
						   .append("<tr><td>Task Description: "+tsk.getDescription()+"</td></tr>")
						   .append("<tr><td>Start Date: "+tsk.getStartDate()+"</td></tr>")
						   .append("<tr><td>Due Date: "+tsk.getDueDate()+"</td></tr>")
						   .append("<tr><td>Employees </td></tr>")
						   .append("<tr><td><table border=\"1\">")
						   .append("<tr><td>MID</td><td>Employee Name</td></tr>");
						   for(Employee emp: tsk.getEmployeesList()){
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
	
	@RequestMapping(method = RequestMethod.GET, value ="/viewProjects.do")
	public ModelAndView viewProjects(HttpServletRequest request)
	{
		return new ModelAndView(ApplicationConstants.VIEW_PROJECTS_PAGE);
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/manageProject.do")
	public void manageProject(@RequestParam("jsonstr") String jsonstr, HttpServletRequest request, HttpServletResponse response){
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		String type = jsonRequest.getString("type");	
		
		JSONObject jsonResponse = new JSONObject();
		JSONUtil jsonUtil = new JSONUtil();
		log.debug("type=: "+type);
		
		
		List<ProjectDTO> allProjList = null;
		
		switch(type){
		
			case "view":
				
					String filterName = jsonRequest.getString("filter");
					String sortOrder = jsonRequest.getString("sortOrder");
					String sortField = jsonRequest.getString("sortField");
					
					if(!filterName.isEmpty() && !"undefined".equalsIgnoreCase(filterName) ){
						log.debug("Executing filter: "+filterName);
						allProjList= taskService.getAllProjects(filterName);
					}else{
							allProjList= taskService.getAllProjects();	
					}
					
					if (!allProjList.isEmpty()) {
						//Gson gson = TaskUtil.getGsonInstance();
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
				
			case "delete":
	
					String id = jsonRequest.getString("id");
					Persistable prj;
					try {
						prj = (Persistable)taskService.find(Project.class, Integer.valueOf(id));
						if(prj !=null){
							taskService.deleteEntity(prj);
						}
					} catch (NumberFormatException | ApplicationException e) {
						log.error("Error Occurred --"+ e);
					}			
					break;
				default:
					log.debug("Manage Projects: Did not match any case");
					break;
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/addProject.do")
	public void addProject(@RequestParam("jsonstr") String jsonstr, HttpServletRequest request, HttpServletResponse response) throws ApplicationException{
		
		JSONObject jsonRequest = new JSONObject(jsonstr);
		
		String type = jsonRequest.getString("type");
		
		String name = jsonRequest.getString("name");
		String desc = jsonRequest.getString("desc");
		
		Project proj=  new Project();
		
		switch(type){
		
		case "add":
			proj.setName(name);
			proj.setDescription(desc);
			taskService.saveEntity(proj);
			break;
		
		case "update":
			String id = jsonRequest.getString("id");
			Project oldProj = (Project) taskService.find(Project.class, Integer.valueOf(id));
			if(oldProj !=null)	{
				proj.setId(Integer.valueOf(id));
				proj.setName(name);
				proj.setDescription(desc);
				taskService.updateEntity(proj);
			}
			break;
		
		default:
			log.debug("Add/Update Projects: Did not match any case");
			break;
		}
		
	}
	
	//http://learnfromexamples.com/generate-excel-in-spring-mvc-application-using-apache-poi/
	
	@RequestMapping(method = RequestMethod.GET, value ="/empreport.do")
	public ModelAndView empReport(HttpServletRequest request, HttpServletResponse response) throws ApplicationException{

		List<Persistable> empList = taskService.getAllEmployees();		
		return new ModelAndView(new ExcelReportView(), "empList", empList);
			
		}
}
