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
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)
			throws IOException, ServletException {

		String key="userName";
		try{
			
			HttpServletRequest req = (HttpServletRequest) request;
			//HttpServletResponse res = (HttpServletResponse) response;
			
			HttpSession session = req.getSession();
			
			User user=SessionManager.getUserInSession(req);
			if(user!=null){
				MDC.put(key, user.getUserName());
			}
			
			URL aURL = new URL(req.getRequestURL().toString());		
			String url = aURL.getPath();
			String sessionTimeOut = req.getParameter("st");
			log.debug("------------------------");
			log.debug("Requested URL: "+aURL);	
			log.debug("------------------------");
			
			chain.doFilter(request, response);
			
			/*if(SessionManager.isUserAuthenticated(req)){
				chain.doFilter(request, response);
			}else{
				if(!"POST".equalsIgnoreCase(req.getMethod())){
					String queryStr = req.getQueryString();
					if(null==queryStr || "".equalsIgnoreCase(queryStr) || "null".equalsIgnoreCase(queryStr)){
						queryStr = "";
					}
					
					if(!(url.contains(loginHomePath) ||  url.contains(loginValidatePath))){
						String accessedPath = url+"?"+queryStr;
						session.setAttribute("accessedPath", accessedPath);
					}
				}
				
				if (session.isNew() || (null!=sessionTimeOut && "1".equals(sessionTimeOut))) {
					 request.setAttribute("errorMessage", MessageCode.LOGIN_SESSION_TIMEOUT);
				}
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("loginpage.do");
			    requestDispatcher.forward(request, response);
			}*/
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
