package com.mindtree.task.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mindtree.task.constants.ApplicationConstants;
import com.mindtree.task.dto.EmployeeDTO;
import com.mindtree.task.dto.FileModelDTO;
import com.mindtree.task.dto.ProjectDTO;
import com.mindtree.task.dto.TaskDTO;
import com.mindtree.task.form.AddTaskForm;
import com.mindtree.task.form.MultipartFileForm;
import com.mindtree.task.reports.ExcelReportView;
import com.mindtree.task.service.TaskService;
import com.mindtree.task.util.TaskUtil;
import com.mindtree.task.validators.FileValidator;
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
		
		List<ProjectDTO> allProjList= taskService.searchProject(null);	
		request.setAttribute("allProjList", allProjList);
		ModelAndView modelview=new ModelAndView();
		modelview.addObject(model);
		modelview.setViewName(ApplicationConstants.ADD_TASK_PAGE);
		return modelview;
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/viewTasks.do")
	public ModelAndView viewTasks(HttpServletRequest request)
	{
		List<ProjectDTO> allProjList= taskService.searchProject(null);	
		StringBuilder sb = new StringBuilder("[");
		int index = 0;
		for(ProjectDTO prj:allProjList){
			if(index>0){sb.append(",");}
			sb.append("'").append(prj.getName()).append(":").append(prj.getId()).append("'");
			index++;
		}
		sb.append("]");
		request.setAttribute("projectsList", sb.toString());	
		
		return new ModelAndView(ApplicationConstants.VIEW_TASK_PAGE);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value ="/viewProjects.do")
	public ModelAndView viewProjects(HttpServletRequest request)
	{
		return new ModelAndView(ApplicationConstants.VIEW_PROJECTS_PAGE);
	}
	
	//http://learnfromexamples.com/generate-excel-in-spring-mvc-application-using-apache-poi/
	
	@RequestMapping(method = RequestMethod.GET, value ="/empreport.do")
	public ModelAndView empReport(HttpServletRequest request, HttpServletResponse response) {

		List<EmployeeDTO> empList = taskService.searchEmployee(null);	
		return new ModelAndView(new ExcelReportView(), "empList", empList);
			
		}
	
	 @RequestMapping(method = RequestMethod.GET, value = "/fileUpload.do" )
	    public ModelAndView showUploadForm(HttpServletRequest request,  Model model) {
	        
		 model.addAttribute("mFileModel", new MultipartFileForm());
		 return new ModelAndView("fileUploadPage");
	    }
		 
	 @RequestMapping(method = RequestMethod.GET, value ="/downloadFile.do")
	    public void download(HttpServletRequest request,
	            @RequestParam String fileId, HttpServletResponse response) {
	 		
	        try {
	        	
	        	FileModelDTO doc = taskService.getFile(Integer.valueOf(fileId));
	        	if(doc !=null){
	        		response.setHeader("Content-Disposition", "inline;filename=\"" +doc.getFileName()+ "\"");
	  	            OutputStream out = response.getOutputStream();
	  	            response.setContentType(doc.getContentType());
	  	            IOUtils.copy(new ByteArrayInputStream(doc.getData()), out);
	  	            out.flush();
	  	            out.close();
	        	}
	        } catch (IOException e) {
	        	log.error("Exception occurred while downloading file", e);
	        }
	    }
		 	
			
			

		@RequestMapping(method = RequestMethod.GET, value = "/viewPDF.do" )
		public ModelAndView viewPDF(HttpServletRequest request,  Model model) {
			 return new ModelAndView("viewPDFPage");
		}
		
		@RequestMapping(method = RequestMethod.GET, value ="/viewEmployees.do")
		public ModelAndView viewEmployees(HttpServletRequest request)
		{
			return new ModelAndView(ApplicationConstants.VIEW_EMPLOYEES_PAGE);
		}
		
	
	/*
	 * All Post Methods
	 */
	
	@RequestMapping(method=RequestMethod.POST, value="/addTask.do")
	public ModelAndView saveTask(HttpServletRequest request,
										HttpServletResponse response,@ModelAttribute("addTaskFormDetails") AddTaskForm addTaskForm, 
										BindingResult result){
		ModelAndView modelview= new ModelAndView();
		TaskValidator taskValidator=new TaskValidator();
		taskValidator.validate(addTaskForm, result);
		
		if(result.hasErrors()){
			List<ProjectDTO> allProjList= taskService.searchProject(null);	
			request.setAttribute("allProjList", allProjList);	
			modelview.setViewName(ApplicationConstants.ADD_TASK_PAGE);
		}else{
			TaskDTO taskdto = new TaskDTO();
			ProjectDTO proj = taskService.getProject(addTaskForm.getProjId());
			taskdto.setProject(proj);
			taskdto.setTaskName(addTaskForm.getTaskName());
			taskdto.setDescription(addTaskForm.getTaskDesc());
			taskdto.setStartDate(TaskUtil.parseDate(addTaskForm.getStartDate()));
			taskdto.setDueDate(TaskUtil.parseDate(addTaskForm.getDueDate()));
			List<String> empArr = addTaskForm.getEmpId();
			List<EmployeeDTO> empDTOList = taskService.getEmployeesForMids(empArr);
			taskdto.getEmployeeDTOList().addAll(empDTOList);
			
			TaskDTO savedTask = taskService.saveTask(taskdto);
			String message="Task Saved Successfully: Id: "+savedTask.getTaskId();
			modelview.addObject("message", message);
			modelview.setViewName(ApplicationConstants.HOME_PAGE);
		}
		return modelview;
	}
	
	 @RequestMapping(value = "/fileUpload.do", method = RequestMethod.POST)
	    public ModelAndView handleFileUpload(HttpServletRequest request,
	            @ModelAttribute("mFileModel") MultipartFileForm mFileModel, BindingResult result) {
		 
		 ModelAndView mv = new ModelAndView("fileUploadPage");
		 FileValidator fileValidator = new FileValidator();
		 fileValidator.validate(mFileModel, result);
		 if(result.hasErrors()){
			 mv.addObject("message", "Error Occurred. Please try again");
		 }else{
			 MultipartFile fileUpload = mFileModel.getFile();
             log.debug("Saving file: " + fileUpload.getOriginalFilename());
             FileModelDTO uploadFile = new FileModelDTO();
           
             try { 
             	uploadFile.setFileName(fileUpload.getOriginalFilename());
					uploadFile.setData(fileUpload.getBytes());
					uploadFile.setContentType(fileUpload.getContentType());
					taskService.saveFile(uploadFile);		
					
					mv.addObject("message", "File Uploaded Successfully");
					
				} catch (IOException e) {
					log.error("Exception occurred while saving file", e);
				}
		 }
	    return mv;
	    }  	 	
		
		
}
