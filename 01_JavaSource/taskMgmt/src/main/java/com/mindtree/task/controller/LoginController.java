package com.mindtree.task.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.mindtree.task.authentication.SessionManager;
import com.mindtree.task.constants.ApplicationConstants;
import com.mindtree.task.constants.MessageCode;
import com.mindtree.task.exception.ApplicationException;
import com.mindtree.task.form.LoginForm;
import com.mindtree.task.model.Role;
import com.mindtree.task.model.User;
import com.mindtree.task.service.LoginService;
import com.mindtree.task.util.ReturnStatus;
import com.mindtree.task.util.TaskUtil;

@Controller
public class LoginController {

	private static final Logger log = Logger.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private TaskUtil taskUtil;

	@RequestMapping(method = RequestMethod.GET, value ="/loginpage.do")
	public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView(ApplicationConstants.LOGIN_PAGE);
		if(SessionManager.isUserAuthenticated(request)){
			mav = new ModelAndView(ApplicationConstants.HOME_PAGE);				
		}
		
		String refreshUrl = taskUtil.timeOutUrl();
		mav.addObject("refreshUrl", refreshUrl);
		
		return mav;
	}
	
	@RequestMapping(value = "/adminHome.do", method = RequestMethod.GET)
	public ModelAndView adminHome(HttpServletRequest request,HttpServletResponse response) {	
		
		return new ModelAndView(ApplicationConstants.ADMIN_HOME_PAGE);
	}
	
	@RequestMapping(value = "adminloginAs.do", method = RequestMethod.GET)
	public ModelAndView loginAs(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("adminLoginAsPage");
	}
	
	@RequestMapping(value = "/logoff.do", method = RequestMethod.GET)
	public ModelAndView logOff(HttpServletRequest request,HttpServletResponse response) {		
		
		String sessionTimeOut = request.getParameter("st");
		if(null!=sessionTimeOut && "1".equals(sessionTimeOut)) {
			request.setAttribute("errorMessage", MessageCode.LOGIN_SESSION_TIMEOUT);
		}else {
			request.setAttribute("errorMessage", MessageCode.LOGOFF_SUCCESSFUL);
		}
				
		//Update LoggedIn Status
		User user = SessionManager.getUserInSession(request);
		if(user !=null){
			user.setLoginStatus("N");
			loginService.updateUser(user);
			
			SessionManager.clearUserInSession(request);		
			SecurityContextHolder.clearContext();
			
			log.info("Session Data Cleared. User Logged Out Successfully:");
		}		
		log.info("-----------------------------------------------------------");
		
		
		ModelAndView mav = new ModelAndView(ApplicationConstants.LOGIN_PAGE);
		return mav;
	}
	
	
	@RequestMapping(value = "/validateLogin.do", method = RequestMethod.POST)
	public ModelAndView validateLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("loginform") LoginForm loginForm) {		
		ModelAndView modelAndView = null;
		try {
			HttpSession session = request.getSession();
			modelAndView = new ModelAndView(ApplicationConstants.LOGIN_PAGE);
			String userName = loginForm.getUserID();
			String password = loginForm.getPassword();
			String locale = loginForm.getLocale();
			
			if (!TaskUtil.isEmptyString(userName) && !TaskUtil.isEmptyString(password)) {
				
				String encrypPasswd = TaskUtil.getEncryptedString(password);
				ReturnStatus returnStatus = loginService.login(userName, encrypPasswd);
				if (null != returnStatus && MessageCode.SUCCESS.equalsIgnoreCase(returnStatus.getStatus())) {
					
					User user = (User) returnStatus.getReturnObject();
					if (null != user) {
						/*Default landing page on successful login*/
						modelAndView.setViewName(ApplicationConstants.HOME_PAGE);						
						SessionManager.setUserInSession(user, request, true);
						
						/*Change default locale & Set Locale in Session*/
						LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
					    localeResolver.setLocale(request, response, new Locale(locale));
						SessionManager.setLocale(locale, request, false);
						
						log.info("------------------------------------------");
						log.info("Logged in User Details");
						log.info("UserName: "+user.getUserName());
						log.info("Locale: "+LocaleContextHolder.getLocale());
						List<Role> rolesList = user.getRoles();
						log.info("Roles:");
						for(Role role: rolesList){
								log.info(" -"+role.getRoleName());
								if(ApplicationConstants.ROLE_ADMIN.equals(role.getRoleName())){
									SessionManager.setAdminUserInSession(user, request, false);
									/*Admin home page for admin users*/
									modelAndView.setViewName(ApplicationConstants.ADMIN_HOME_PAGE);
								}
						}
						log.info("------------------------------------------");		
						
						/*Save Logged In Status*/
						user.setLoginStatus("Y");
						user.setLastLogin(new Date());
						loginService.updateUser(user);
						
						/*Setting Refresh URL in JSP header
						 * After session Time out it redirects to login page */
						 						
						String refreshUrl = taskUtil.timeOutUrl();
						session.setAttribute("refreshUrl", refreshUrl);

						
						 /* The accessed path [link in url] is stored in session object
						 * if its null then forward request to welcome page else to the selected link 
						 */
						 
						String accessedURL = (String)request.getSession().getAttribute("accessedURL");
							if (accessedURL != null) {
								session.removeAttribute("accessedURL");
								response.sendRedirect(accessedURL);
							}
					} 

				} else {
					String errorCode = (null != returnStatus) ? returnStatus.getErrorCode() : "error";
					modelAndView.addObject(ApplicationConstants.ERROR_MESSAGE, errorCode);
				}

			} else {
				log.debug("LoginService returned error "+MessageCode.EMPTY_LOGIN_CREDENTIALS);
				modelAndView.addObject(ApplicationConstants.ERROR_MESSAGE, MessageCode.EMPTY_LOGIN_CREDENTIALS);
			}

		}catch (Exception ex) {
			String errorMessage = "Login Process Failed";
			log.error("ERROR Occurred", ex);
			throw new ApplicationException(errorMessage, ex);
		}		
		return modelAndView;
	}
	
	@RequestMapping(value = "/workas.do", method = RequestMethod.POST)
	public ModelAndView workAsOtherUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("loginform") LoginForm loginForm) {		
		
		ModelAndView modelAndView = new ModelAndView(ApplicationConstants.HOME_PAGE);
		
		try{
			String userName = loginForm.getUserID();
			User currentUser = SessionManager.getUserInSession(request);
				if (!TaskUtil.isEmptyString(userName)){
					
					if(!userName.equalsIgnoreCase(currentUser.getUserName())){
						
						ReturnStatus returnStatus = loginService.loginAs(userName);
						
						if (null != returnStatus && MessageCode.SUCCESS.equalsIgnoreCase(returnStatus.getStatus())) {
							User user = (User) returnStatus.getReturnObject();
							if (null != user) {
								SessionManager.setUserInSession(user, request, false);
								SessionManager.setLoginAs(true, request, false);		
								log.info("------------------------------------------");
								log.info("User in session: "+user.getUserName());
							    log.info("------------------------------------------");	
							}
						}else {
							log.error("LoginAsService returned error "+MessageCode.USER_NOT_FOUND);
							modelAndView.setViewName(ApplicationConstants.LOGIN_AS_PAGE);
							modelAndView.addObject(ApplicationConstants.ERROR_MESSAGE, "User Not found");
						}
						
					}else{
						modelAndView.setViewName(ApplicationConstants.LOGIN_AS_PAGE);
						modelAndView.addObject(ApplicationConstants.ERROR_MESSAGE, "User already logged in");
					}
					
				}else {
					log.error("LoginAsService returned error "+MessageCode.USER_NOT_FOUND);
					modelAndView.setViewName(ApplicationConstants.LOGIN_AS_PAGE);
					modelAndView.addObject(ApplicationConstants.ERROR_MESSAGE, "User field empty");
				}
			
		}catch (Exception ex) {
			String errorMessage = "Work as Other user process Failed";
			log.error("ERROR Occurred", ex);
			throw new ApplicationException(errorMessage, ex);
		}	
		
		return modelAndView;
	}
	
	@RequestMapping(value = "adminReturnHome.do", method = RequestMethod.GET)
	public ModelAndView returnToAdminHome(HttpServletRequest request,HttpServletResponse response) {	
		
		SessionManager.setLoginAs(false, request, false);
		//change user in session as admin user object
		User adminUserObj = SessionManager.getAdminUserInSession(request);
		SessionManager.setUserInSession(adminUserObj, request, false);
		return new ModelAndView(ApplicationConstants.ADMIN_HOME_PAGE);
	}
}
