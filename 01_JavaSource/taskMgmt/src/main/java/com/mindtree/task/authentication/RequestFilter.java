package com.mindtree.task.authentication;

import java.io.IOException;
import java.net.URL;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.mindtree.task.model.User;

public class RequestFilter implements javax.servlet.Filter{
	
private static final Logger log = Logger.getLogger(RequestFilter.class);

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
			//String sessionTimeOut = request.getParameter("st");
			
			//HttpSession session = request.getSession();
			
			User user=SessionManager.getUserInSession(request);
			if(user!=null){
				MDC.put(key, user.getUserName());
			}
			
			URL aURL = new URL(request.getRequestURL().toString());		
			log.debug("------------------------");
			log.debug("Requested URL: "+aURL.getPath());	
			log.debug("------------------------");
			
			chain.doFilter(request, response);
			
		} finally{
			MDC.remove(key);
		}  
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {			
		/*
		 *  Do Nothing 
		 */
	}

}
