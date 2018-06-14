package com.mindtree.task.authentication;

import java.io.IOException;
import java.net.URL;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.mindtree.task.constants.MessageCode;
import com.mindtree.task.model.User;

public class RequestFilter implements javax.servlet.Filter{
	
private static final Logger log = Logger.getLogger(RequestFilter.class);

private String loginHomePath;
private String loginValidatePath;	

private final String loginHome = "loginHome";	
private final String loginValidate = "loginValidate";

	@Override
	public void destroy() {
		/*
		 *  Do Nothing 
		 */
	}

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse,FilterChain chain)
			throws IOException, ServletException {

		String key="userName";
		try{
			
			HttpServletRequest request = (HttpServletRequest) srequest;
			HttpServletResponse response = (HttpServletResponse) sresponse;
			HttpSession session = request.getSession();
			
			User user=SessionManager.getUserInSession(request);
			if(user!=null){
				MDC.put(key, user.getUserName());
			}
			
			URL aURL = new URL(request.getRequestURL().toString());		
			log.debug("------------------------");
			log.debug("Requested URL: "+aURL.getPath());	
			log.debug("------------------------");
			
			String url = aURL.getPath();
						
			if(!url.contains("logoff.do") && "GET".equalsIgnoreCase(request.getMethod())){
				if(!url.contains(loginHomePath)){
					String queryStr = request.getQueryString();
					if(null==queryStr || "".equalsIgnoreCase(queryStr) || "null".equalsIgnoreCase(queryStr)){
						queryStr = "";
					}
					String accessedURL = url + "?"+queryStr;
					session.setAttribute("accessedURL", accessedURL);
				}
			}
						
			chain.doFilter(request, response);
			
		} finally{
			MDC.remove(key);
		}  
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {	
		 loginHomePath = filterConfig.getInitParameter(loginHome);
		 loginValidatePath = filterConfig.getInitParameter(loginValidate);
	}

}
