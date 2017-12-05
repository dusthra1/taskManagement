package com.mindtree.task.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mindtree.task.model.User;

public class SessionManager {
	
	public static final String USER_SESSION_KEY = "user";
		
	public static final String ADMIN_USER_SESSION_KEY = "adminUser";
	
	public static final String LOCALE = "locale";
	
	public static final String LOGIN_AS_SESSION_ACTIVE = "loginAs";
	
	private SessionManager(){
		
	}

	public static void setUserInSession(User user, HttpServletRequest request,boolean createSession) {		
		HttpSession session = request.getSession(true);
		session.setAttribute(USER_SESSION_KEY, user);		
	}
	
	public static void setAdminUserInSession(User user, HttpServletRequest request,boolean createSession){
		HttpSession session = request.getSession(false);
		session.setAttribute(ADMIN_USER_SESSION_KEY, user);	
	}
	
	public static void setLocale(String locale, HttpServletRequest request,boolean createSession){
		HttpSession session = request.getSession(false);
		session.setAttribute(LOCALE, locale);	
	}
	
	public static void setLoginAs(boolean loginAs, HttpServletRequest request,boolean createSession){
		HttpSession session = request.getSession(false);
		session.setAttribute(LOGIN_AS_SESSION_ACTIVE, loginAs);	
	}
	
	public static boolean isUserAuthenticated(HttpServletRequest request) {
		
		boolean authenticated = false;

		User user = getUserInSession(request);
		if (null != user) {
			authenticated = true;
		} else {
			authenticated = false;
		}			
		return authenticated;
	}

	
	public static User getUserInSession(HttpServletRequest request) {		
		HttpSession session = request.getSession(false);
		User user = null;
		if (session != null) {
			user = (User) session.getAttribute(USER_SESSION_KEY);
		}		
		return user;
	}
	
	public static User getAdminUserInSession(HttpServletRequest request) {		
		HttpSession session = request.getSession(false);
		User user = null;
		if (session != null) {
			user = (User) session.getAttribute(ADMIN_USER_SESSION_KEY);
		}		
		return user;
	}

	
	public static void clearUserInSession(HttpServletRequest request) {		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(USER_SESSION_KEY);
			session.invalidate();
		}		
	}

}
